package com.slowv.youtuberef.service.dto;

import com.slowv.youtuberef.entity.VideoEntity;
import com.slowv.youtuberef.entity.enums.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String url;
    private String description;
    private VideoStatus status = VideoStatus.DRAFT;

    public static VideoDto from(@NonNull final VideoEntity entity) {
        return VideoDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .description(entity.getDescription())
                .status(entity.getStatus())
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
