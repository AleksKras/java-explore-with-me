package ru.practicum.ewm.mapper;


import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;


public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    Compilation toCompilation(CompilationDto compilationDto);

    Compilation toCompilation(NewCompilationDto newCompilationDto);

}
