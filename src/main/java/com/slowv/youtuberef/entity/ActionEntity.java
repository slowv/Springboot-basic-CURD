package com.slowv.youtuberef.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Table
@Entity(name = "actions")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionEntity extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    public ActionEntity setName(final String name) {
        this.name = name;
        return this;
    }
}
