package com.slowv.youtuberef.repository.specification;

import com.slowv.youtuberef.entity.VideoEntity;
import com.slowv.youtuberef.entity.enums.VideoStatus;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VideoSpecification {

    private static final String FIELD_URL = "url";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_STATUS = "status";

    private final List<Specification<VideoEntity>> specifications = new ArrayList<>();

    public static VideoSpecification builder() {
        return new VideoSpecification();
    }

    public VideoSpecification withUrl(final String url) {
        if (!ObjectUtils.isEmpty(url)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(criteriaBuilder.upper(root.get(FIELD_URL)), like(url))
            );
        }
        return this;
    }

    public VideoSpecification withDescription(final String description) {
        if (!ObjectUtils.isEmpty(description)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(criteriaBuilder.upper(root.get(FIELD_DESCRIPTION)), like(description))
            );
        }
        return this;
    }

    public VideoSpecification withStatuses(final List<VideoStatus> statuses) {
        if (!ObjectUtils.isEmpty(statuses)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            root.get(FIELD_STATUS).in(statuses)
            );
        }
        return this;
    }

    private static String like(final String value) {
        return "%" + value.toUpperCase() + "%";
    }

    public Specification<VideoEntity> build() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(specifications.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }
}
