package ru.practicum.ewm.model.dto;

import lombok.Data;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
public class CommentDto {
    private long id;
    private UserDto Author;
    private EventDto event;
    private String text;
    private LocalDateTime created;
    private boolean modified;
}
