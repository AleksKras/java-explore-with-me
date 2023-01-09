package ru.practicum.ewm.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.CommentService;
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

    private final CommentService commentService;

    //events
    @GetMapping("/{id}/events")
    public List<EventDto> getUserEvents(@PathVariable Long id,
                                        @RequestParam(value = "from", defaultValue = "0") int from,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Get запрос к эндпоинту: /users/events, id = " + id);
        return eventService.getByUserId(id, from, size);
    }

    @PatchMapping("/{id}/events")
    public EventDto updateEvent(@RequestBody UpdateEventDto eventDto,
                                @PathVariable Long id) {
        log.info("Получен patch запрос к эндпоинту: /users/events");
        return eventService.update(eventDto, id);
    }

    @PostMapping("/{id}/events")
    public EventDto createEvent(@RequestBody @Valid NewEventDto newEventDto,
                                @PathVariable Long id) {
        log.info("Получен post запрос к эндпоинту: /users/events");
        return eventService.create(newEventDto, id);
    }

    @GetMapping("/{id}/events/{eventId}")
    public EventDto getUserEvents(@PathVariable Long id,
                                  @PathVariable Long eventId) {
        log.info("Получен Get запрос к эндпоинту: /users/events, id = " + id);
        return eventService.getEventByUserId(id, eventId);
    }

    @PatchMapping("/{id}/events/{eventId}")
    public EventDto rejectUserEvent(@PathVariable Long id,
                                    @PathVariable Long eventId) {
        log.info("Получен Patch запрос к эндпоинту: /users/events, id = " + id);
        return eventService.rejectEventByUserId(id, eventId);
    }

    @GetMapping("/{id}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Long id,
                                                          @PathVariable Long eventId) {
        log.info("Получен Get запрос к эндпоинту: /users/events, id = " + id);
        return requestService.getEventRequests(id, eventId);
    }

    @PatchMapping("/{id}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequiest(@PathVariable Long id,
                                                   @PathVariable Long eventId,
                                                   @PathVariable Long reqId) {
        log.info("Получен Patch запрос к эндпоинту: /users/events, id = " + id);
        return requestService.confirmRequest(id, eventId, reqId);
    }

    @PatchMapping("/{id}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long id,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        log.info("Получен Patch запрос к эндпоинту: /users/events, id = " + id);
        return requestService.rejectRequest(id, eventId, reqId);
    }

    //requests
    @GetMapping("/{id}/requests")
    public List<ParticipationRequestDto> getUserEvents(@PathVariable Long id) {
        log.info("Получен Get запрос к эндпоинту: /users/request, id = " + id);
        return requestService.getByUserId(id);
    }

    @PostMapping("/{id}/requests")
    public ParticipationRequestDto createRequest(@PathVariable Long id,
                                                 @RequestParam(value = "eventId") long eventId) {
        log.info("Получен post запрос к эндпоинту: /users/request, id = " + id);
        return requestService.create(id, eventId);
    }

    @PatchMapping("/{id}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelByUser(@PathVariable Long id,
                                                @PathVariable Long requestId) {
        log.info("Получен Patch запрос к эндпоинту: /users/request, id = " + id);
        return requestService.cancelRequest(id, requestId);
    }

    //comment

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto createComment(@RequestBody @Valid NewCommentDto newCommentDto,
                                    @PathVariable Long userId,
                                    @PathVariable long eventId) {
        log.info("Получен Post запрос к эндпоинту: /users/events/comments, id = " + userId);
        return commentService.create(newCommentDto, userId, eventId);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto updateComment(@RequestBody @Valid NewCommentDto newCommentDto,
                                    @PathVariable Long userId,
                                    @PathVariable long commentId) {
        log.info("Получен Patch запрос к эндпоинту: /users/comments, id = " + userId);
        return commentService.update(newCommentDto, userId, commentId);
    }

    @GetMapping("/comments/{userId}")
    public List<CommentDto> getAllCommentByUser(@PathVariable Long userId,
                                                @RequestParam(value = "from", defaultValue = "0") int from,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Post запрос к эндпоинту: /users/events/comments, id = " + userId);
        return commentService.getAllByUser(userId,from,size);
    }

}
