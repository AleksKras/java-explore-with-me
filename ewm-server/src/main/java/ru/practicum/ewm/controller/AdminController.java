package ru.practicum.ewm.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.*;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/admin/")
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    private final CommentService commentService;

    //events
    @GetMapping("/events")
    public List<EventDto> getAllEvents(@RequestParam(value = "users", required = false) List<Long> users,
                                       @RequestParam(value = "states", required = false) List<String> states,
                                       @RequestParam(value = "categories", required = false) List<Long> categories,
                                       @RequestParam(value = "rangeStart", required = false)
                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam(value = "rangeEnd", required = false)
                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(value = "from", defaultValue = "0") int from,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Get запрос к эндпоинту: /admin/events");
        return eventService.getAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{id}")
    public EventDto updateEvent(@RequestBody NewEventDto eventDto,
                                @PathVariable Long id) {
        log.info("Получен Put запрос к эндпоинту: /admin/events");
        return eventService.update(eventDto, id);
    }

    @PatchMapping("/events/{id}/publish")
    public EventDto publishEvent(@PathVariable Long id) {
        log.info("Получен Patch запрос к эндпоинту: /admin/events");
        return eventService.publishEvent(id);
    }

    @PatchMapping("/events/{id}/reject")
    public EventDto rejectEvent(@PathVariable Long id) {
        log.info("Получен Patch запрос к эндпоинту: /admin/events");
        return eventService.rejectEvent(id);
    }

    //categories
    @PatchMapping("/categories")
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Получен Patch запрос к эндпоинту: /admin/categories");
        return categoryService.update(categoryDto);
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Получен Post запрос к эндпоинту: /admin/categories");
        return categoryService.create(categoryDto);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        log.info("Получен Patch запрос к эндпоинту: /admin/categories. Удаление категории:" + id);
        categoryService.delete(id);
    }

    //users
    @PostMapping("/users")
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        log.info("Получен Post запрос к эндпоинту: /admin/users");
        return userService.create(userDto);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUser(@RequestParam(value = "ids") Long[] users,
                                    @RequestParam(value = "from", defaultValue = "0") int from,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен Get запроск эндпоинту: /users");
        return userService.getAll(users, from, size);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        log.info("Получен Delete запрос к эндпоинту: /users. Удаление user:" + id);
        userService.delete(id);
    }

//compilations

    @PostMapping("/compilations")
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Получен Post запрос к эндпоинту: /admin/compilations");
        return compilationService.create(compilationDto);
    }

    @DeleteMapping("/compilations/{id}")
    public void deleteCompilation(@PathVariable long id) {
        log.info("Получен Delete запрос к эндпоинту: /compilation. Удаление Compilation:" + id);
        compilationService.delete(id);
    }

    @DeleteMapping("/compilations/{id}/events/{eventId}")
    public void deleteCompilationEvent(@PathVariable long id,
                                       @PathVariable long eventId) {
        log.info("Получен Delete запрос к эндпоинту: /compilation. Удаление Compilation:" + id);
        compilationService.deleteEvent(id, eventId);
    }

    @PatchMapping("/compilations/{id}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long id,
                                      @PathVariable long eventId) {
        log.info("Получен patch запрос к эндпоинту: /compilation. Добавление события");
        compilationService.addEvent(id, eventId);
    }

    @PatchMapping("/compilations/{id}/pin")
    public void pinCompilation(@PathVariable long id) {
        log.info("Получен patch запрос к эндпоинту: /compilation. Закрепление.");
        compilationService.pinCompilation(id);
    }

    @DeleteMapping("/compilations/{id}/pin")
    public void unpinCompilation(@PathVariable long id) {
        log.info("Получен patch запрос к эндпоинту: /compilation. открепление.");
        compilationService.unpinCompilation(id);
    }

    //comments

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        log.info("Получен Post запрос к эндпоинту: /comments, id = " + commentId);
        commentService.delete(commentId);
    }

}