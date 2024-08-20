package com.slowv.youtuberef.entity;

import com.slowv.youtuberef.entity.enums.VideoStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "video")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoEntity extends AbstractAuditingEntity<String> {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.DRAFT;
}
