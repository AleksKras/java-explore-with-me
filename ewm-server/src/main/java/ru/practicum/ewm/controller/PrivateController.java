package ru.practicum.ewm.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/users/")
public class PrivateController {

    private final EventService eventService;
    private final RequestService requestService;

    //events
    @GetMapping("/{id}/events")
    public List<EventDto> getUserEvents(@PathVariable(required = true) Long id,
                                        @RequestParam(value = "from", defaultValue = "0") int from,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Get запрос к эндпоинту: /users/events, id = " + id);
        return eventService.getByUserId(id, from, size);
    }

    @PatchMapping("/{id}/events")
    public EventDto updateEvent(@RequestBody UpdateEventDto eventDto,
                                @PathVariable(required = true) Long id) {
        log.info("Получен patch запрос к эндпоинту: /users/events");
        return eventService.update(eventDto, id);
    }

    @PostMapping("/{id}/events")
    public EventDto createEvent(@RequestBody @Valid NewEventDto newEventDto,
                                @PathVariable(required = true) Long id) {
        log.info("Получен post запрос к эндпоинту: /users/events");
        return eventService.create(newEventDto, id);
    }

    @GetMapping("/{id}/events/{eventId}")
    public EventDto getUserEvents(@PathVariable(required = true) Long id,
                                  @PathVariable(required = true) Long eventId) {
        log.info("Получен Get запрос к эндпоинту: /users/events, id = " + id);
        return eventService.getEventByUserId(id, eventId);
    }

    @PatchMapping("/{id}/events/{eventId}")
    public EventDto rejectUserEvent(@PathVariable(required = true) Long id,
                                    @PathVariable(required = true) Long eventId) {
        log.info("Получен Patch запрос к эндпоинту: /users/events, id = " + id);
        return eventService.rejectEventByUserId(id, eventId);
    }

    @GetMapping("/{id}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable(required = true) Long id,
                                             @PathVariable(required = true) Long eventId) {
        log.info("Получен Get запрос к эндпоинту: /users/events, id = " + id);
        return requestService.getEventRequests(id, eventId);
    }

    @PatchMapping("/{id}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequiest(@PathVariable(required = true) Long id,
                                      @PathVariable(required = true) Long eventId,
                                      @PathVariable(required = true) Long reqId) {
        log.info("Получен Patch запрос к эндпоинту: /users/events, id = " + id);
        return requestService.confirmRequest(id, eventId, reqId);
    }

    @PatchMapping("/{id}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable(required = true) Long id,
                                    @PathVariable(required = true) Long eventId,
                                    @PathVariable(required = true) Long reqId) {
        log.info("Получен Patch запрос к эндпоинту: /users/events, id = " + id);
        return requestService.rejectRequest(id, eventId, reqId);
    }

    //requests
    @GetMapping("/{id}/requests")
    public List<ParticipationRequestDto> getUserEvents(@PathVariable(required = true) Long id) {
        log.info("Получен Get запрос к эндпоинту: /users/request, id = " + id);
        return requestService.getByUserId(id);
    }

    @PostMapping("/{id}/requests")
    public ParticipationRequestDto createRequest(@PathVariable(required = true) Long id,
                                                 @RequestParam(value = "eventId", required = true) long eventId) {
        log.info("Получен post запрос к эндпоинту: /users/request, id = " + id);
        return requestService.create(id, eventId);
    }

    @PatchMapping("/{id}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelByUser(@PathVariable(required = true) Long id,
                                    @PathVariable(required = true) Long requestId) {
        log.info("Получен Patch запрос к эндпоинту: /users/request, id = " + id);
        return requestService.cancelRequest(id, requestId);
    }
}
