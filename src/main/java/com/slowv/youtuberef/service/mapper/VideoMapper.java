package com.slowv.youtuberef.service.mapper;

import com.slowv.youtuberef.entity.VideoEntity;
import com.slowv.youtuberef.service.dto.VideoDto;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface VideoMapper extends EntityMapper<VideoDto, VideoEntity> {
}
