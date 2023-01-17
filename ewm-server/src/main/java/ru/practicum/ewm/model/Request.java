package ru.practicum.ewm.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REQUESTS")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUESTER_ID")
    private User requester;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
