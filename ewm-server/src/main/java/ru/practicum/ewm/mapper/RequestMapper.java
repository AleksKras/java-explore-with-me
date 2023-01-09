package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.dto.RequestDto;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    Request toRequest(RequestDto requestDto);

    RequestDto toDto(Request request);

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto toParticipationRequestDto(Request request);
}
