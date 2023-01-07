package ru.practicum.ewm.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.restclient.StatClient;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.CommentService;
import ru.practicum.ewm.service.CompilationService;
import ru.practicum.ewm.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class PublicController {

    private final EventService eventService;
    private final CompilationService compilationService;
    private final CategoryService categoryService;

    private final CommentService commentService;

    private final StatClient statClient;

    //Events
    @GetMapping("/events/{id}")
    public EventDto getEvents(@PathVariable(required = true) Long id,
                              HttpServletRequest request) {
        log.info("Получен Get запрос к эндпоинту: /events, id = " + id);
        statClient.postHit(new HitDto("ewm-server",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()));

        EventDto eventDto = eventService.getById(id, request.getRequestURI());

        return eventDto;
    }

    @GetMapping("/events")
    public List<EventShortDto> getAllEvents(@RequestParam(value = "text", required = false) String text,
                                            @RequestParam(value = "categories", required = false) Long[] categories,
                                            @RequestParam(value = "paid", defaultValue = "false") boolean paid,
                                            @RequestParam(value = "rangeStart", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(value = "rangeEnd", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(value = "onlyAvailable", defaultValue = "false") boolean onlyAvailable,
                                            @RequestParam(value = "sort", required = false) String sort,
                                            @RequestParam(value = "from", defaultValue = "0") int from,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            HttpServletRequest request) {
        log.info("Получен Get запрос к эндпоинту: /events");
        statClient.postHit(new HitDto("ewm-server",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()));
        return eventService.getAllShort(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    //Compilations

    @GetMapping("/compilations")
    public List<CompilationDto> getAllCompilations(@RequestParam(value = "pinned", defaultValue = "false") boolean pinned,
                                                   @RequestParam(value = "from", defaultValue = "0") int from,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Get запрос к эндпоинту: /compilations");
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/compilations/{id}")
    public CompilationDto getCompilation(@PathVariable(required = true) long id) {
        log.info("Получен Get запрос к эндпоинту: /events, id = " + id);
        return compilationService.getById(id);
    }

    //Categories

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(value = "from", defaultValue = "0") int from,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Get запрос к эндпоинту: /categories");
        return categoryService.getAll(from, size);
    }

    @GetMapping("/categories/{id}")
    public CategoryDto getCategory(@PathVariable(required = true) long id) {
        log.info("Получен Get запрос к эндпоинту: /categories, id = " + id);
        return categoryService.getById(id);
    }

    @GetMapping("/comments/{eventId}")
    public List<CommentDto> GetAllCommentByEvent(@PathVariable(required = true) Long eventId,
                                                @RequestParam(value = "from", defaultValue = "0") int from,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Post запрос к эндпоинту: /events/, id = " + eventId);
        return commentService.getAllByEvent(eventId,from,size);
    }


}
