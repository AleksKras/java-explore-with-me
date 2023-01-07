package ru.practicum.statsserver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsserver.model.HitDto;
import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class StatController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public HitDto create(@RequestBody HitDto hitDto) {
        log.info("Получен Post запрос к эндпоинту: /hit");
        return statsService.create(hitDto);
    }

    @GetMapping("/stats")
    public List<Stats> getStats(@RequestParam(value = "start", required = false)
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startRange,
                                @RequestParam(value = "end", required = false)
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endRange,
                                @RequestParam(value = "uris", required = false) String uris,
                                @RequestParam(value = "unique", required = false) boolean unique) {
        log.info("Получен Get запроск эндпоинту: /stats");
        return statsService.getStats(startRange, endRange, uris, unique);
    }
}
