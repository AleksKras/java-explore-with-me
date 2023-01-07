package ru.practicum.ewm.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EVENTS")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "ANNOTATION", nullable = false)
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    private transient int confirmedRequests;
    @Column(name = "CREATED_ON", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "EVENT_DATE", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INITIATOR_ID")
    private User initiator;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;
    @Column(name = "PAID", nullable = false)
    private boolean paid;
    @Column(name = "PARTICIPANT_LIMIT", nullable = false)
    private int participantLimit;
    @Column(name = "PUBLESHED_ON")
    private LocalDateTime publishedOn;
    @Column(name = "REQUEST_MODERATION", nullable = false)
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "TITLE", nullable = false)
    private String title;
    private transient long views;
}
