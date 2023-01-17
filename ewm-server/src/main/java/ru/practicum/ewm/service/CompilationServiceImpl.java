package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.mapper.PageMapper;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto compilationDto) {

        return compilationMapper.toDto(compilationRepository.save(compilationMapper.toCompilation(compilationDto)));
    }

    @Override
    public CompilationDto getById(long id) {
        return compilationMapper.toDto(compilationRepository.getReferenceById(id));
    }

    @Override
    public List<CompilationDto> getAll(boolean pined, int from, int size) {
        Page<Compilation> compilations;
        if (pined) {
            compilations = compilationRepository.findAllByPinned(pined, PageMapper.getPagable(from, size));
        } else {
            compilations = compilationRepository.findAll(PageMapper.getPagable(from, size));
        }
        List<Compilation> compilationList = new ArrayList<>();
        if (compilations != null && compilations.hasContent()) {
            compilationList = compilations.getContent();
        }
        List<CompilationDto> compilationDtoList = new ArrayList<>();

        for (Compilation compilation : compilationList) {
            compilationDtoList.add(compilationMapper.toDto(compilation));
        }
        return compilationDtoList;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Compilation compilation = compilationRepository.getReferenceById(id);
        compilationRepository.delete(compilation);
    }

    @Override
    @Transactional
    public void deleteEvent(long id, long eventId) {
        Compilation compilation = compilationRepository.getReferenceById(id);
        List<Event> listEvents = compilation.getEvents();
        listEvents.remove(eventRepository.getReferenceById(eventId));
        compilation.setEvents(listEvents);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void addEvent(long id, long eventId) {
        Compilation compilation = compilationRepository.getReferenceById(id);
        List<Event> listEvents = compilation.getEvents();
        listEvents.add(eventRepository.getReferenceById(eventId));
        compilation.setEvents(listEvents);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void pinCompilation(long id) {
        Compilation compilation = compilationRepository.getReferenceById(id);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void unpinCompilation(long id) {
        Compilation compilation = compilationRepository.getReferenceById(id);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }
}
