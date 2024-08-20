package com.slowv.youtuberef.service.dto;

import com.slowv.youtuberef.entity.enums.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
}
