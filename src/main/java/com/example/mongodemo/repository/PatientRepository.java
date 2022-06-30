package com.example.mongodemo.repository;

import com.example.mongodemo.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findPatientByEmail(String email);
}
