package com.slowv.youtuberef.service.mapper;

import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.service.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface AccountMapper extends EntityMapper<AccountDto, AccountEntity> {
}
