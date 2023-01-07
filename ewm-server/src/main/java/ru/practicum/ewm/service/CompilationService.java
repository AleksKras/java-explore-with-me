package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto compilationDto);

    CompilationDto getById(long id);

    List<CompilationDto> getAll(boolean pined, int from, int size);

    void delete(long id);

    void deleteEvent(long id, long eventId);

    void addEvent(long id, long eventId);

    void pinCompilation(long id);

    void unpinCompilation(long id);

}
