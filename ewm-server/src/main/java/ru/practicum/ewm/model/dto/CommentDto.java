package ru.practicum.ewm.model.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class CommentDto {
    private Long id;
    private UserDto author;
    private EventDto event;
    private String text;
    private LocalDateTime created;
    private boolean modified;
}
