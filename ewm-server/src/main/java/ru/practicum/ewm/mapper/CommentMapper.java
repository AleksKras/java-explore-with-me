package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentDto commentDto);

    CommentDto toDto(Comment comment);

}
