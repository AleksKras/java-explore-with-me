package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByAuthor_Id(Long authorId, Pageable pageable);

    Page<Comment> findAllByEvent_Id(Long eventId, Pageable pageable);

    List<Comment> findAllByEvent_Id(Long eventId);
}
