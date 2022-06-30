package com.example.mongodemo.controller;

import com.example.mongodemo.model.Doctor;
import com.example.mongodemo.model.Specialization;
import com.example.mongodemo.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping(path = "{doctorId}")
    public Doctor fetchById(@PathVariable("doctorId") String id) {
        return doctorService.getById(id);
    }

    @GetMapping
    public List<Doctor> getDoctors(@RequestParam(required = false) List<Specialization> specializations) {
        if (specializations == null || specializations.isEmpty()) {
            return doctorService.getAllDoctors();
        }
        return doctorService.getDoctorsBySpecializations(specializations);
    }

    @PostMapping
    public Doctor registerNewDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @DeleteMapping(path = "{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") String id) {
        doctorService.deleteDoctorById(id);
    }

    @PutMapping(path = "{doctorId}")
    public Doctor addDoctorSpecialization(@PathVariable("doctorId") String id,
                                          @RequestParam List<Specialization> specializations) {
        return doctorService.updateSpecializations(id, specializations);
    }
}
