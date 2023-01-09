package ru.practicum.ewm.mapper;


import org.mapstruct.MappingTarget;

import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.EventDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.NewEventDto;
import ru.practicum.ewm.model.dto.UpdateEventDto;

public interface EventMapper {
    EventDto toDto(Event event);

    Event toEvent(EventDto eventDto);

    EventShortDto toShortDto(Event event);

    Event toEventFromNew(NewEventDto newEventDto);

    void updateEventFromDto(UpdateEventDto updateEventDto, @MappingTarget Event event);

    void updateEventFromDto(NewEventDto newEventDto, Event event);
}
