package com.slowv.youtuberef.service.dto;

import com.slowv.youtuberef.entity.enums.VideoStatus;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Không được để trống")
    private String url;
    private String description;
    private VideoStatus status = VideoStatus.DRAFT;
    private Instant updatedAt;
}
