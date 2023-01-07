package ru.practicum.ewm.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewCompilationDto {
    List<Long> events;
    boolean pinned;
    @NotBlank
    String title;
}
