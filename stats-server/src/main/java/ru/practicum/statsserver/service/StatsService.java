package ru.practicum.statsserver.service;

import ru.practicum.statsserver.model.HitDto;
import ru.practicum.statsserver.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    HitDto create(HitDto hitDto);

    List<Stats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
