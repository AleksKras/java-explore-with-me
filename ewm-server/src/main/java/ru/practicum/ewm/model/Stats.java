package ru.practicum.ewm.model;

import lombok.Data;

@Data
public class Stats {
    private String app;
    private String uri;
    private long hits;
}
