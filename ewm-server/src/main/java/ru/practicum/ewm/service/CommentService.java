package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.CommentDto;
import ru.practicum.ewm.model.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto create(NewCommentDto newCommentDto, long userId, long eventId);

    CommentDto update(NewCommentDto newCommentDto, long userId, long commentId);

    List<CommentDto> getAllByUser(long userId, int from, int size);

    List<CommentDto> getAllByEvent(long eventId, int from, int size);

    void delete(long commentId);

}
