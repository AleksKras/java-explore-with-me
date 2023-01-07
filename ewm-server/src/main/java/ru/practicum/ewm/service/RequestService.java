package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getEventRequests(long id, long eventId);

    ParticipationRequestDto confirmRequest(long id, long eventId, long reqId);

    ParticipationRequestDto rejectRequest(long id, long eventId, long reqId);

    List<ParticipationRequestDto> getByUserId(long id);

    ParticipationRequestDto create(long id, long eventId);

    ParticipationRequestDto cancelRequest(long id, long requestId);
}
