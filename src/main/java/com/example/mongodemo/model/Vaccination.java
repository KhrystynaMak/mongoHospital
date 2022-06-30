package com.example.mongodemo.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Vaccination {
    private String id;
    private String name;
    private int shotNumber;
    private Set<Symptoms> consequences;
    private LocalDateTime injected;
    private String doctorId;

    public Vaccination(String name, int shotNumber, Set<Symptoms> consequences, LocalDateTime injected, String doctorId) {
        this.name = name;
        this.shotNumber = shotNumber;
        this.consequences = consequences;
        this.injected = injected;
        this.doctorId = doctorId;
    }
}
