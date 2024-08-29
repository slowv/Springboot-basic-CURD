package com.slowv.youtuberef.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(
        name = "accounts",
        indexes = {
                @Index(
                        columnList = "username",
                        unique = true
                )
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity extends AbstractAuditingEntity<String> {
    @Id
    @UuidGenerator
    String id;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    String username;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @Column(name = "uuid", unique = true, nullable = false)
    String uuid;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<RoleEntity> roles = new ArrayList<>();
}
