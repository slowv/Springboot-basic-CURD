package com.slowv.youtuberef.entity;

import com.slowv.youtuberef.entity.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
public class RoleEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    @UuidGenerator
    String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    ERole name;
}
