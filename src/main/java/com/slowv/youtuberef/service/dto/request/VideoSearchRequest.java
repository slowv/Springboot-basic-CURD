package com.slowv.youtuberef.service.dto.request;

import com.slowv.youtuberef.entity.VideoEntity;
import com.slowv.youtuberef.entity.enums.VideoStatus;
import com.slowv.youtuberef.repository.specification.VideoSpecification;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoSearchRequest extends FilterRequest<VideoEntity> {
    String url;
    String description;
    List<VideoStatus> status = new ArrayList<>();

    @Override
    public Specification<VideoEntity> specification() {
        return VideoSpecification.builder()
                .withUrl(this.url)
                .withDescription(this.description)
                .withStatuses(this.status)
                .build();
    }
}
