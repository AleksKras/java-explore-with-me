package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime created;
    EventDto event;
    UserDto requester;
    RequestStatus status;
}
