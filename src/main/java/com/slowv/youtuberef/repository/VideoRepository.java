package com.slowv.youtuberef.repository;

import com.slowv.youtuberef.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, String> {
    List<VideoEntity> findAllByIdIn(List<String> ids);
}
