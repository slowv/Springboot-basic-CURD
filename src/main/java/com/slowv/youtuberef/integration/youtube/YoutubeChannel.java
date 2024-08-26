package com.slowv.youtuberef.integration.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.slowv.youtuberef.config.YoutubeConfiguration;
import com.slowv.youtuberef.integration.youtube.model.YoutubeItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class YoutubeChannel {
    private static final String GOOGLE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_SEARCH_TYPE = "video";
    private static final String YOUTUBE_SEARCH_FIELDS = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)";
    private static final String YOUTUBE_API_APPLICATION = "google-youtube-api-search";
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

    private List<YoutubeItem> mappedBy(final List<SearchResult> results) {
        return results
                .stream()
                .map(result -> new YoutubeItem(
                        result.getId().getVideoId(),
                        result.getSnippet().getTitle(),
                        result.getSnippet().getThumbnails().getStandard().getUrl(),
                        result.getSnippet().getDescription()
                ))
                .toList();
    }
}
