package ru.practicum.ewm.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {
    private long id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}
