package com.example.mongodemo.repository;

import com.example.mongodemo.model.Doctor;
import com.example.mongodemo.model.Specialization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findAllBySpecializationsContains(List<Specialization> specializations);
}
