package com.example.mongodemo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document
public class Doctor extends Person {
    @Id
    private String id;
    private List<Specialization> specializations;

    public Doctor(String firstName, String lastName, String email, Gender gender, Address address, LocalDate birthDate) {
        super(firstName, lastName, email, gender, address, birthDate);
    }

}
