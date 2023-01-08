package ru.practicum.ewm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "COMPILATIONS")
@Data
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany
    @JoinTable(
            name = "COMPILATION_EVENT",
            joinColumns = {@JoinColumn(name = "COMPILATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID")}
    )
    private List<Event> events;
    @Column(name = "PINNED", nullable = false)
    private boolean pinned;
    @Column(name = "TITLE", nullable = false)
    @Size(min = 1, max = 128)
    private String title;
}
