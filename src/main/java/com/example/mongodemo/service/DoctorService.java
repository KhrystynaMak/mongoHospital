package com.example.mongodemo.service;

import com.example.mongodemo.model.Doctor;
import com.example.mongodemo.model.Specialization;
import com.example.mongodemo.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getById(String id) {
        return doctorRepository.findById(id).orElseThrow(() -> noDoctorWithId(id));
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.insert(doctor);
    }

    public void deleteDoctorById(String id) {
        if (!doctorRepository.existsById(id)) {
            throw noDoctorWithId(id);
        }
        doctorRepository.deleteById(id);
    }

    private IllegalStateException noDoctorWithId(String id) {
        return new IllegalStateException("There is no doctor with such id: " + id);
    }

    public Doctor updateSpecializations(String doctorId, List<Specialization> specializations) {
        Doctor doctor = getById(doctorId);
        doctor.setSpecializations(specializations);
        return saveDoctor(doctor);
    }

    public List<Doctor> getDoctorsBySpecializations(List<Specialization> specializations) {
        return doctorRepository.findAllBySpecializationsContains(specializations);
    }
}
