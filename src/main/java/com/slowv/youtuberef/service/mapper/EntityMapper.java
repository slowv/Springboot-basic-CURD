package com.slowv.youtuberef.service.mapper;

import org.mapstruct.*;

import java.util.List;

public interface EntityMapper<D, E> {
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    E toEntity(D dto);

//    @Named("toDto")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    D toDto(E entity);

    List<D> toDto(List<E> entities);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    List<E> toEntity(List<D> dtos);

    @Named(value = "update")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(D dto, @MappingTarget E entity);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateDto(@MappingTarget D dto, E entity);
}
