package ru.practicum.ewm.service;

import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.EventDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.NewEventDto;
import ru.practicum.ewm.model.dto.UpdateEventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventDto> getAll(List<Long> users,
                          List<String> states,
                          List<Long> categories,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd,
                          int from,
                          int size);

    List<EventShortDto> getAllShort(String text,
                                    Long[] categories,
                                    boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    boolean onlyAvailable,
                                    String sort,
                                    int from,
                                    int size);

    EventDto create(NewEventDto newEventDto, long userId);

    EventDto getById(long id, String requestUri);

    Event getEventById(long id);

    EventDto getEventByUserId(long id, long eventId);

    List<EventDto> getByUserId(long id, int from, int size);

    EventDto update(NewEventDto eventDto, long eventId);

    EventDto update(UpdateEventDto eventDto, long userId);

    EventDto publishEvent(long id);

    EventDto rejectEvent(long id);

    EventDto rejectEventByUserId(long id, long eventId);
}
