package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.PageMapper;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.model.dto.EventDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.NewEventDto;
import ru.practicum.ewm.model.dto.UpdateEventDto;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.restclient.StatClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    RequestRepository requestRepository;
    UserService userService;
    EventMapper eventMapper;

    CommentService commentService;

    StatClient statClient;

    @Override
    public List<EventDto> getAll(List<Long> users,
                                 List<String> states,
                                 List<Long> categories,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 int from,
                                 int size) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        List<EventState> eventStates = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                eventStates.add(EventState.valueOf(state));
            }
        } else {
            eventStates = null;
        }

        Page events;

        if (rangeEnd != null) {
            events = eventRepository.allEventsByUserWithEndDate(users,
                    eventStates,
                    categories,
                    rangeStart,
                    rangeEnd,
                    PageMapper.getPagable(from, size));
        } else {
            events = eventRepository.allEventsByUser(users,
                    eventStates,
                    categories,
                    rangeStart,
                    PageMapper.getPagable(from, size));
        }

        List<Event> eventList = new ArrayList<>();
        if (events != null && events.hasContent()) {
            eventList = events.getContent();
        }
        List<EventDto> eventDtoList = new ArrayList<>();

        for (Event event : eventList) {
            EventDto eventDto = eventMapper.toDto(event);
            eventDto.setComments(commentService.getAllByEvent(event.getId()));
            eventDtoList.add(eventDto);
        }
        return eventDtoList;
    }

    @Override
    public List<EventShortDto> getAllShort(String text,
                                           Long[] categories,
                                           boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           boolean onlyAvailable,
                                           String sort, int from, int size) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        List<Long> categoriesList = List.of(categories);

        Page events;
        if (rangeEnd != null) {
            events = eventRepository.allEventsShortWithEndDate("%" + text + "%",
                    categoriesList,
                    paid,
                    rangeStart,
                    rangeEnd,
                    PageMapper.getPagable(from, size));
        } else {
            events = eventRepository.allEventsShort("%" + text + "%",
                    categoriesList,
                    paid,
                    rangeStart,
                    PageMapper.getPagable(from, size));
        }

        List<Event> eventList = new ArrayList<>();
        if (events != null && events.hasContent()) {
            eventList = events.getContent();
        }
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        List<String> requestUrl = new ArrayList<>();
        LocalDateTime minCreateDate = LocalDateTime.now();
        for (Event event : eventList) {
            Long evenId = event.getId();
            requestUrl.add("/events/" + evenId);

            if (minCreateDate.isAfter(event.getCreatedOn())) {
                minCreateDate = event.getCreatedOn();
            }

        }
        List<Stats> statsList = statClient.getStat(minCreateDate, LocalDateTime.now(), requestUrl, false);

        for (Event event : eventList) {
            Long evenId = event.getId();
            long hits = 0;
            for (Stats stats : statsList) {
                if (stats.getUri().equals("/events/" + evenId)) {
                    hits = hits + stats.getHits();
                }
            }
            event.setViews(hits);
            event.setConfirmedRequests(requestRepository.countAllByStatusIsAndEvent_id(RequestStatus.CONFIRMED, evenId));
            eventShortDtoList.add(eventMapper.toShortDto(event));
        }
        return eventShortDtoList;
    }

    @Override
    public EventDto create(NewEventDto newEventDto, long userId) {
        User user = userService.getUserById(userId);
        Event event = eventMapper.toEventFromNew(newEventDto);
        event.setInitiator(user);
        return eventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public EventDto getById(long id, String requestUrl) {
        Event event = eventRepository.getReferenceById(id);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ValidationException("Неверный статус события");
        }
        event.setConfirmedRequests(requestRepository.countAllByStatusIsAndEvent_id(RequestStatus.PENDING, id));
        List<Stats> statsList = statClient.getStat(event.getCreatedOn(), LocalDateTime.now(), List.of(requestUrl), false);
        long hits = 0;
        for (Stats stats : statsList) {
            hits = hits + stats.getHits();
        }
        event.setViews(hits);
        EventDto eventDto = eventMapper.toDto(event);
        eventDto.setComments(commentService.getAllByEvent(event.getId()));
        return eventDto;
    }

    @Override
    public Event getEventById(long id) {
        return eventRepository.getReferenceById(id);
    }

    @Override
    public EventDto getEventByUserId(long id, long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getInitiator().getId() != id) {
            throw new ForbiddenException("Неверный ID инициатора");
        }
        EventDto eventDto = eventMapper.toDto(event);
        eventDto.setComments(commentService.getAllByEvent(event.getId()));
        return eventDto;
    }

    @Override
    public List<EventDto> getByUserId(long id, int from, int size) {

        Page events = eventRepository.findAllByInitiator_Id(id, PageMapper.getPagable(from, size));

        List<Event> eventList = new ArrayList<>();
        if (events != null && events.hasContent()) {
            eventList = events.getContent();
        }
        List<EventDto> eventDtoList = new ArrayList<>();

        for (Event event : eventList) {
            eventDtoList.add(eventMapper.toDto(event));
        }

        return eventDtoList;
    }

    @Override
    public EventDto update(NewEventDto eventDto, long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        eventMapper.updateEventFromDto(eventDto, event);
        return eventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public EventDto update(UpdateEventDto eventDto, long userId) {
        Event event = eventRepository.getReferenceById(eventDto.getEventId());
        if (event.getInitiator().getId() != userId) {
            throw new ForbiddenException("Неверный ID инициатора");
        }
        eventMapper.updateEventFromDto(eventDto, event);
        return eventMapper.toDto(eventRepository.save(event));

    }

    @Override
    public EventDto publishEvent(long id) {
        Event event = eventRepository.getReferenceById(id);
        if (event.getState() == EventState.PENDING && event.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
            event.setState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
            eventRepository.save(event);
        } else {
            throw new ValidationException("Ошибка публикации события");
        }
        return eventMapper.toDto(event);
    }

    @Override
    public EventDto rejectEvent(long id) {
        Event event = eventRepository.getReferenceById(id);
        if (event.getState() == EventState.PUBLISHED) {
            throw new ValidationException("Отмена доступна для не опубликованных событий");
        } else {
            event.setState(EventState.CANCELED);
            eventRepository.save(event);
        }
        return eventMapper.toDto(event);
    }

    @Override
    public EventDto rejectEventByUserId(long id, long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getInitiator().getId() != id) {
            throw new ForbiddenException("Пользователь не является инициатором Event");
        }
        if (event.getState() != EventState.PENDING) {
            throw new ValidationException("Отмена доступна для событий на модерации");
        } else {
            event.setState(EventState.CANCELED);
            eventRepository.save(event);
        }
        return eventMapper.toDto(event);
    }
}
