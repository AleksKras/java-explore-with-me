package ru.practicum.ewm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false)
    @Size(min = 1, max = 64)
    private String name;
    @Column(name = "EMAIL", nullable = false)
    @Size(min = 1, max = 64)
    private String email;
}
