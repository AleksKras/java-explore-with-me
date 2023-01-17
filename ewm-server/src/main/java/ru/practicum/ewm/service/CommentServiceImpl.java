package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.PageMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.CommentDto;
import ru.practicum.ewm.model.dto.NewCommentDto;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventRepository eventRepository;

    private final CommentMapper commentMapper;


    @Override
    @Transactional
    public CommentDto create(NewCommentDto newCommentDto, long userId, long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        User user = userService.getUserById(userId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ValidationException("Событие должно быть опубликовано");
        }
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setText(newCommentDto.getText());
        comment.setModified(false);
        comment.setCreated(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(NewCommentDto newCommentDto, long userId, long commentId) {
        Comment comment = commentRepository.getReferenceById(commentId);
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
    public List<CommentDto> getAllByEvent(long eventId) {
        List<Comment> commentList = commentRepository.findAllByEvent_Id(eventId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentDtoList.add(commentMapper.toDto(comment));
        }
        return commentDtoList;
    }

    @Override
    @Transactional
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
