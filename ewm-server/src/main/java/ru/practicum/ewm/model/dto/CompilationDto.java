package ru.practicum.ewm.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {
    long id;
    List<EventShortDto> events;
    boolean pinned;
    String title;
}
