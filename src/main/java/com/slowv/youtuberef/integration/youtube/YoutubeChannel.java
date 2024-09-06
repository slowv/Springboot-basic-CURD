package com.slowv.youtuberef.integration.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.slowv.youtuberef.config.YoutubeConfiguration;
import com.slowv.youtuberef.integration.youtube.model.YoutubeItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class YoutubeChannel {
    private static final String GOOGLE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_SEARCH_TYPE = "video";
    private static final String YOUTUBE_SEARCH_FIELDS = "items(id/kind,id/videoId,id/channelId,id/playlistId,snippet/title,snippet/description,snippet/publishedAt,snippet/channelId,snippet/channelTitle,snippet/thumbnails/default/url,snippet/thumbnails/medium/url,snippet/thumbnails/high/url),nextPageToken,prevPageToken,pageInfo/totalResults,pageInfo/resultsPerPage";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    private final YouTube youtube;
    private final YoutubeConfiguration youtubeConfiguration;

    @SneakyThrows
    public List<YoutubeItem> search(String searchQuery, int maxSearch) {
        log.info("Starting YouTube search... {}", searchQuery);

        // Define the API request for retrieving search results.
        final var search = youtube.search().list(List.of("id", "snippet"));

        search.setKey(youtubeConfiguration.getApiKey());
        search.setQ(searchQuery);

        // Restrict the search results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type

        search.setType(List.of(YOUTUBE_SEARCH_TYPE));
        search.setFields(YOUTUBE_SEARCH_FIELDS);

        if (maxSearch < 1) {
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
        } else {
            search.setMaxResults((long) maxSearch);
        }

        final var searchResponse = search.execute();
        final var searchResultList = searchResponse.getItems();

        return Optional.ofNullable(searchResultList)
                .map(this::mappedBy)
                .orElse(List.of());
    }

    @SneakyThrows
    private List<YoutubeItem> mappedBy(final List<SearchResult> results) {
        // Lấy thông số video
        final var videos = this.youtube.videos().list(List.of("statistics"));
        videos.setId(results.stream().map(r -> r.getId().getVideoId()).toList());
        final var responseVideo = videos.execute();
        final var videoMap = responseVideo.getItems().stream()
                .collect(Collectors.toMap(Video::getId, Video::getStatistics, (existing, replacement) -> existing));

        // Lấy danh sách channel
        final var channels = this.youtube.channels().list(List.of("id", "snippet"));
        channels.setId(results.stream()
                .map(r -> r.getSnippet().getChannelId())
                .filter(Objects::nonNull)
                .toList()
        );
        channels.setFields("items(snippet(title,thumbnails(default,medium,high)))");
        final var responseChannel = channels.execute();
        final var channelMap = responseChannel.getItems().stream()
                .collect(Collectors.toMap(Channel::getId, Channel::getSnippet, (existing, replacement) -> existing));

        return results.stream()
                .map(searchResult -> {
                    final var videoId = searchResult.getId().getVideoId();
                    final var channelId = searchResult.getId().getChannelId();

                    final var videoStat = videoMap.get(videoId);
                    final var channelSnippet = channelMap.get(channelId);
                    return new YoutubeItem(
                            GOOGLE_YOUTUBE_URL + videoId,
                            searchResult.getSnippet().getTitle(),
                            searchResult.getSnippet().getThumbnails().getMedium().getUrl(),
                            searchResult.getSnippet().getDescription(),
                            videoStat.getCommentCount(),
                            videoStat.getDislikeCount(),
                            videoStat.getLikeCount(),
                            videoStat.getViewCount(),
                            channelSnippet.getTitle(),
                            channelSnippet.getThumbnails().getMedium().getUrl()
                    );
                })
                .toList();
    }
}
