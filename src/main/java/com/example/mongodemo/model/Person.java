package com.example.mongodemo.model;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Data
public class Person {
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private Gender gender;
    private Address address;
    private LocalDate birthDate;
    private LocalDateTime registered;
    @Transient
    private Integer age;

    public Person(String firstName, String lastName, String email, Gender gender, Address address, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.birthDate = birthDate;
        this.registered = LocalDateTime.now();
    }

    public Integer getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
}
