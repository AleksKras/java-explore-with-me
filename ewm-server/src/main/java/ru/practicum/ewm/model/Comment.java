package ru.practicum.ewm.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private User author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;
    @Column(name = "TEXT", nullable = false)
    private String text;
    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;
    @Column(name = "MODIFIED", nullable = false)
    private boolean modified;

}
