package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @NotBlank
    String annotation;
    @NotNull
    Long category;
    @NotBlank
    String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime eventDate;
    @NotNull
    Location location;
    boolean paid;
    int participantLimit;
    boolean requestModeration;
    @NotBlank
    String title;
}