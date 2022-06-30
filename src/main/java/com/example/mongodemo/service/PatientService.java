package com.example.mongodemo.service;

import com.example.mongodemo.model.Patient;
import com.example.mongodemo.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient addNewPatient(Patient patient) {
        validateEmailIsNotOccupied(patient.getEmail());

        System.out.println("Inserting patient: " + patient);
        return patientRepository.insert(patient);
    }

    public void deletePatientById(String id) {
        if (!patientRepository.existsById(id)) {
            throw noPatientById(id);
        }

        System.out.println("Deleting patient with id: " + id);
        patientRepository.deleteById(id);
    }

    private IllegalStateException noPatientById(String id) {
        return new IllegalStateException("There is no patient with such id: " + id);
    }

    private void validateEmailIsNotOccupied(String email) {
        Optional<Patient> existedPatient = patientRepository.findPatientByEmail(email);
        existedPatient.ifPresent(p -> {
            throw new IllegalStateException("Patient with email '" + email + "' already exists.");
        });
    }

    @Transactional
    public Patient updatePatient(String patientId, String newName, String newEmail) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> noPatientById(patientId));

        if (valueRelevantForUpdate(newName, patient.getFirstName())) {
            patient.setFirstName(newName);
        }

        if (valueRelevantForUpdate(newEmail, patient.getEmail())) {
            validateEmailIsNotOccupied(newEmail);
            patient.setEmail(newEmail);
        }

        return patientRepository.save(patient);
    }

    private boolean valueRelevantForUpdate(String newValue, String oldValue) {
        return newValue != null && !newValue.isBlank() && !newValue.equals(oldValue);
    }
}

