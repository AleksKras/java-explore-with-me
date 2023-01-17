package ru.practicum.ewm.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LOCATIONS")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "LAT", nullable = false)
    private double lat;
    @Column(name = "LON", nullable = false)
    private double lon;
}
