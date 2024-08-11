package com.slowv.youtuberef.service;

import com.slowv.youtuberef.service.dto.VideoDto;
import com.slowv.youtuberef.service.dto.request.VideoSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.util.List;

public interface VideoService {
    VideoDto getVideo(@NonNull final String id);

    Page<VideoDto> getVideos(final VideoSearchRequest request);

    VideoDto create(@NonNull final VideoDto dto);

    VideoDto update(@NonNull final VideoDto dto);

    void delete(@NonNull final List<String> ids);
}
