package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getEventRequests(long id, long eventId) {
        User user = userRepository.getReferenceById(id);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getInitiator().getId() != id) {
            throw new ForbiddenException("Неверный ID инициатора");
        }
        List<Request> requestList = requestRepository.findAllByEvent_id(event.getId());
        List<ParticipationRequestDto> participationRequestDtoList = new ArrayList<>();
        for (Request request : requestList) {
            participationRequestDtoList.add(requestMapper.toParticipationRequestDto(request));
        }
        return participationRequestDtoList;
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(long id, long eventId, long reqId) {
        Request request = requestRepository.getReferenceById(reqId);
        if (request.getEvent().getId() != eventId) {
            throw new ValidationException("Неверный ID события");
        }
        if (request.getEvent().getInitiator().getId() != id) {
            throw new ForbiddenException("Неверный ID инициатора");
        }
        request.setStatus(RequestStatus.CONFIRMED);
        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(long id, long eventId, long reqId) {
        Request request = requestRepository.getReferenceById(reqId);
        if (request.getEvent().getId() != eventId) {
            throw new ValidationException("Неверный ID события");
        }
        if (request.getEvent().getInitiator().getId() != id) {
            throw new ForbiddenException("Неверный ID инициатора");
        }
        request.setStatus(RequestStatus.REJECTED);
        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getByUserId(long id) {
        User user = userRepository.getReferenceById(id);
        List<Request> requestList = requestRepository.findAllByRequesterIs(user);
        List<ParticipationRequestDto> participationRequestDtoList = new ArrayList<>();
        for (Request request : requestList) {
            participationRequestDtoList.add(requestMapper.toParticipationRequestDto(request));
        }
        return participationRequestDtoList;
    }

    @Override
    @Transactional
    public ParticipationRequestDto create(long id, long eventId) {
        User user = userRepository.getReferenceById(id);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getInitiator().equals(user)) {
            throw new ValidationException("Запрещено создвать запрос к своему событию");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ValidationException("Запрещено создвать запрос к неопубликованному событию");
        }
        if (requestRepository.countAllByStatusIsAndEvent_id(RequestStatus.CONFIRMED, eventId)
                >= event.getParticipantLimit()) {
            throw new ValidationException("Достигнуто максимальное количество участников");
        }
        if (requestRepository.countAllByRequester_IdAndEvent_id(user.getId(), event.getId()) != 0) {
            throw new ValidationException("Уже существует запрос на данное событие");
        }
        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        if (event.isRequestModeration()) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long id, long requestId) {
        Request request = requestRepository.getReferenceById(requestId);
        if (request.getRequester().getId() != id) {
            throw new ForbiddenException("Неверный ID инициатора");
        }
        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}
