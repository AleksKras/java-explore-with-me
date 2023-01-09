package ru.practicum.ewm.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.CategoryService;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class EventMapperImpl implements EventMapper {
    private final CategoryService categoryService;

    @Override
    public EventDto toDto(Event event) {
        if (event == null) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setId(event.getId());
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

    @Override
    public Event toEvent(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }

        Event event = new Event();

        event.setId(eventDto.getId());
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

    @Override
    public EventShortDto toShortDto(Event event) {
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

    @Override
    public Event toEventFromNew(NewEventDto newEventDto) {
        if (newEventDto == null) {
            return null;
        }

        Event event = new Event();

        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(categoryService.getCategoryById(newEventDto.getCategory()));
        event.setCreatedOn(LocalDateTime.now());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setState(EventState.PENDING);

        return event;
    }

    @Override
    public void updateEventFromDto(NewEventDto newEventDto, Event event) {
        if (newEventDto == null) {
            return;
        }

        if (newEventDto.getAnnotation() != null) {
            event.setAnnotation(newEventDto.getAnnotation());
        }
        if (newEventDto.getCategory() != null) {
            event.setCategory(categoryService.getCategoryById(newEventDto.getCategory()));
        }
        if (newEventDto.getDescription() != null) {
            event.setDescription(newEventDto.getDescription());
        }
        if (newEventDto.getEventDate() != null) {
            event.setEventDate(newEventDto.getEventDate());
        }
        if (newEventDto.getLocation() != null) {
            event.setLocation(newEventDto.getLocation());
        }
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        if (newEventDto.getTitle() != null) {
            event.setTitle(newEventDto.getTitle());
        }
    }

    @Override
    public void updateEventFromDto(UpdateEventDto updateEventDto, Event event) {
        if (updateEventDto == null) {
            return;
        }

        if (updateEventDto.getEventId() != null) {
            event.setId(updateEventDto.getEventId());
        }
        if (updateEventDto.getCategory() != null) {
            event.setCategory(categoryService.getCategoryById(updateEventDto.getCategory()));
        }
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getEventDate() != null) {
            event.setEventDate(updateEventDto.getEventDate());
        }
        event.setPaid(updateEventDto.isPaid());
        event.setParticipantLimit(updateEventDto.getParticipantLimit());
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
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

    protected LocationDto locationToLocationDto(Location location) {
        if (location == null) {
            return null;
        }

        LocationDto locationDto = new LocationDto();

        locationDto.setLat(location.getLat());
        locationDto.setLon(location.getLon());

        return locationDto;
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

    protected void categoryDtoToCategory1(CategoryDto categoryDto, Category mappingTarget) {
        if (categoryDto == null) {
            return;
        }

        if (categoryDto.getId() != null) {
            mappingTarget.setId(categoryDto.getId());
        }
        if (categoryDto.getName() != null) {
            mappingTarget.setName(categoryDto.getName());
        }
    }

    protected void userDtoToUser1(UserDto userDto, User mappingTarget) {
        if (userDto == null) {
            return;
        }

        mappingTarget.setId(userDto.getId());
        if (userDto.getName() != null) {
            mappingTarget.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            mappingTarget.setEmail(userDto.getEmail());
        }
    }
}
