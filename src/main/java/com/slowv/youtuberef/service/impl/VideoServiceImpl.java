package com.slowv.youtuberef.service.impl;

import com.slowv.youtuberef.entity.VideoEntity;
import com.slowv.youtuberef.entity.enums.VideoStatus;
import com.slowv.youtuberef.repository.VideoRepository;
import com.slowv.youtuberef.service.VideoService;
import com.slowv.youtuberef.service.dto.VideoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    public VideoDto getVideo(@NonNull final String id) {
        return videoRepository.findById(id)
                .map(VideoDto::from)
                .orElseThrow(() -> new RuntimeException("Video không tồn tại với id là: " + id));
    }

    @Override
    public Page<VideoDto> getVideos() {
        return null;
    }

    @Override
    public VideoDto create(@NonNull final VideoDto dto) {
        final VideoEntity entity = dto.toEntity();
        return VideoDto.from(videoRepository.save(entity));
    }

    @Override
    public VideoDto update(@NonNull final VideoDto dto) {
        final String id = dto.getId();
        if (videoRepository.existsById(id)) {
            final VideoEntity entity = dto.toEntity();
            return VideoDto.from(videoRepository.save(entity));
        }
        throw new RuntimeException("Không tìm thấy video với id là :" + id);
    }

    @Override
    public void delete(@NonNull final List<String> ids) {
        final List<VideoEntity> videos = videoRepository.findAllByIdIn(ids);
        videos.forEach(video -> video.setStatus(VideoStatus.DELETED));
        videoRepository.saveAll(videos);
    }
}
