package com.slowv.youtuberef.service.dto;

import com.slowv.youtuberef.entity.VideoEntity;
import com.slowv.youtuberef.entity.enums.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String url;
    private String description;
    private VideoStatus status = VideoStatus.DRAFT;
    private Instant updatedAt;

    public static VideoDto from(@NonNull final VideoEntity entity) {
        return VideoDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .updatedAt(entity.getLastModifiedDate())
                .build();
    }

    public VideoEntity toEntity() {
        return VideoEntity
                .builder()
                .id(this.getId())
                .url(this.getUrl())
                .description(this.getDescription())
                .status(this.getStatus())
                .build();
    }
}
