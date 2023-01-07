package ru.practicum.statsserver.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statsserver.mapper.HitMapper;
import ru.practicum.statsserver.model.HitDto;
import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatRepository statRepository;
    private final HitMapper hitMapper;

    @Override
    public HitDto create(HitDto hitDto) {
        if (hitDto.getCreated() == null) {
            hitDto.setCreated(LocalDateTime.now());
        }
        return hitMapper.toDto(statRepository.save(hitMapper.toHit(hitDto)));
    }

    @Override
    public List<Stats> getStats(LocalDateTime start, LocalDateTime end, String uris, boolean unique) {
        if (unique) {
            return statRepository.GetUniqueStat(uris, start, end);
        } else {
            return statRepository.GetStat(uris, start, end);
        }

    }
}
