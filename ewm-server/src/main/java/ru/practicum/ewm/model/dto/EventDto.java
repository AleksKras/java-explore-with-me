package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    private UserDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private EventState state;
    private String title;
    private long views;
    private List<CommentDto> comments;
}
