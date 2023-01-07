package ru.practicum.statsserver.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Data
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "app", nullable = false)
    private String app;
    @NotBlank
    @Column(name = "uri", nullable = false)
    private String uri;
    @NotBlank
    @Column(name = "ip", nullable = false)
    private String ip;
    @Column(name =  "CREATED", nullable = false)
    private LocalDateTime created;
}
