package ru.practicum.statsserver.mapper;

import org.mapstruct.Mapper;
import ru.practicum.statsserver.model.Hit;
import ru.practicum.statsserver.model.HitDto;

@Mapper(componentModel = "spring")
public interface HitMapper {
    Hit toHit(HitDto hitDto);

    HitDto toDto(Hit hit);
}