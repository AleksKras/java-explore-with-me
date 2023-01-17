package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private RequestStatus status;
}
