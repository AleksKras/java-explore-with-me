package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import org.hibernate.type.TrueFalseType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.PageMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.model.dto.CommentDto;
import ru.practicum.ewm.model.dto.NewCommentDto;
import ru.practicum.ewm.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    UserService userService;
    EventService eventService;

    CommentMapper commentMapper;


    @Override
    public CommentDto create(NewCommentDto newCommentDto, long userId, long eventId) {
        Event event = eventService.getEventById(eventId);
        User user = userService.getUserById(userId);
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setText(newCommentDto.getText());
        comment.setModified(false);
        comment.setCreated(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(NewCommentDto newCommentDto, long userId, long сommentId) {
        Comment comment = commentRepository.getReferenceById(сommentId);
        if (comment.getAuthor().getId() != userId) {
            throw new ValidationException("Неверный ID автора");
        }
        comment.setText(newCommentDto.getText());
        comment.setModified(true);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getAllByUser(long userId, int from, int size) {
        Page<Comment> commentPage = commentRepository.findAllByAuthor_Id(userId, PageMapper.getPagable(from, size));
        return pageToDoList(commentPage);
    }

    @Override
    public List<CommentDto> getAllByEvent(long eventId, int from, int size) {
        Page<Comment> commentPage = commentRepository.findAllByEvent_Id(eventId, PageMapper.getPagable(from, size));
        return pageToDoList(commentPage);
    }

    @Override
    public void delete(long commentId) {
        Comment comment = commentRepository.getReferenceById(commentId);
        commentRepository.delete(comment);
    }

    private List<CommentDto> pageToDoList(Page<Comment> commentPage) {
        List<Comment> commentList = new ArrayList<>();
        if (commentPage != null && commentPage.hasContent()) {
            commentList = commentPage.getContent();
        }
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentDtoList.add(commentMapper.toDto(comment));
        }
        return commentDtoList;
    }


}
