package ru.practicum.ewm.mapper;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.EventService;

@Component
@Slf4j
@AllArgsConstructor
public class CompilationMapperImpl implements CompilationMapper {

    EventService eventService;

    @Override
    public CompilationDto toDto(Compilation compilation) {
        if (compilation == null) {
            return null;
        }

        CompilationDto compilationDto = new CompilationDto();

        compilationDto.setId(compilation.getId());
        compilationDto.setEvents(eventListToEventShortDtoList(compilation.getEvents()));
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());

        return compilationDto;
    }

    @Override
    public Compilation toCompilation(CompilationDto compilationDto) {
        if (compilationDto == null) {
            return null;
        }

        Compilation compilation = new Compilation();

        compilation.setId(compilationDto.getId());
        compilation.setEvents(eventShortDtoListToEventList(compilationDto.getEvents()));
        compilation.setPinned(compilationDto.isPinned());
        compilation.setTitle(compilationDto.getTitle());

        return compilation;
    }

    @Override
    public Compilation toCompilation(NewCompilationDto compilationDto) {
        if (compilationDto == null) {
            return null;
        }

        Compilation compilation = new Compilation();

        compilation.setEvents(eventDtoListToEventList(compilationDto.getEvents()));
        compilation.setPinned(compilationDto.isPinned());
        compilation.setTitle(compilationDto.getTitle());

        return compilation;
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    protected UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        if (user.getId() != null) {
            userDto.setId(user.getId());
        }
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    protected LocationDto locationToLocationDto(Location location) {
        if (location == null) {
            return null;
        }

        LocationDto locationDto = new LocationDto();

        locationDto.setLat(location.getLat());
        locationDto.setLon(location.getLon());

        return locationDto;
    }

    protected EventDto eventToEventDto(Event event) {
        if (event == null) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategory(categoryToCategoryDto(event.getCategory()));
        eventDto.setConfirmedRequests(event.getConfirmedRequests());
        eventDto.setCreatedOn(event.getCreatedOn());
        eventDto.setDescription(event.getDescription());
        eventDto.setEventDate(event.getEventDate());
        eventDto.setInitiator(userToUserDto(event.getInitiator()));
        eventDto.setLocation(locationToLocationDto(event.getLocation()));
        eventDto.setPaid(event.isPaid());
        eventDto.setParticipantLimit(event.getParticipantLimit());
        eventDto.setPublishedOn(event.getPublishedOn());
        eventDto.setRequestModeration(event.isRequestModeration());
        eventDto.setState(event.getState());
        eventDto.setTitle(event.getTitle());
        eventDto.setViews(event.getViews());

        return eventDto;
    }

    protected List<Long> eventListToEventDtoList(List<Event> list) {
        if (list == null) {
            return null;
        }

        List<Long> list1 = new ArrayList<>();
        for (Event event : list) {
            list1.add(event.getId());
        }

        return list1;
    }

    protected Category categoryDtoToCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        Category category = new Category();

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        return category;
    }

    protected User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        return user;
    }

    protected Location locationDtoToLocation(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }

        Location location = new Location();

        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());

        return location;
    }

    protected Event eventDtoToEvent(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }

        Event event = new Event();

        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(categoryDtoToCategory(eventDto.getCategory()));
        event.setConfirmedRequests(eventDto.getConfirmedRequests());
        event.setCreatedOn(eventDto.getCreatedOn());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setInitiator(userDtoToUser(eventDto.getInitiator()));
        event.setLocation(locationDtoToLocation(eventDto.getLocation()));
        event.setPaid(eventDto.isPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setPublishedOn(eventDto.getPublishedOn());
        event.setRequestModeration(eventDto.isRequestModeration());
        event.setState(eventDto.getState());
        event.setTitle(eventDto.getTitle());
        event.setViews(eventDto.getViews());

        return event;
    }

    protected List<Event> eventDtoListToEventList(List<Long> list) {
        if (list == null) {
            return null;
        }
        log.info(list.toString());
        List<Event> list1 = new ArrayList<>();
        for (Long id : list) {
            list1.add(eventService.getEventById(id));
        }

        return list1;
    }

    protected EventShortDto eventToEventShortDto(Event event) {
        if (event == null) {
            return null;
        }

        EventShortDto eventShortDto = new EventShortDto();

        eventShortDto.setId(event.getId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(categoryToCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setDescription(event.getDescription());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setInitiator(userToUserDto(event.getInitiator()));
        eventShortDto.setPaid(event.isPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(event.getViews());

        return eventShortDto;
    }

    protected List<EventShortDto> eventListToEventShortDtoList(List<Event> list) {
        if (list == null) {
            return null;
        }

        List<EventShortDto> list1 = new ArrayList<EventShortDto>(list.size());
        for (Event event : list) {
            list1.add(eventToEventShortDto(event));
        }

        return list1;
    }


    protected Event eventShortDtoToEvent(EventShortDto eventShortDto) {
        if (eventShortDto == null) {
            return null;
        }

        Event event = new Event();

        event.setId(eventShortDto.getId());
        event.setAnnotation(eventShortDto.getAnnotation());
        event.setCategory(categoryDtoToCategory(eventShortDto.getCategory()));
        event.setConfirmedRequests(eventShortDto.getConfirmedRequests());
        event.setDescription(eventShortDto.getDescription());
        event.setEventDate(eventShortDto.getEventDate());
        event.setInitiator(userDtoToUser(eventShortDto.getInitiator()));
        event.setPaid(eventShortDto.isPaid());
        event.setTitle(eventShortDto.getTitle());
        event.setViews(eventShortDto.getViews());

        return event;
    }

    protected List<Event> eventShortDtoListToEventList(List<EventShortDto> list) {
        if (list == null) {
            return null;
        }

        List<Event> list1 = new ArrayList<Event>(list.size());
        for (EventShortDto eventShortDto : list) {
            list1.add(eventShortDtoToEvent(eventShortDto));
        }

        return list1;
    }
}
