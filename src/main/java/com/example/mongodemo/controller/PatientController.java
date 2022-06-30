package com.example.mongodemo.controller;

import com.example.mongodemo.model.Patient;
import com.example.mongodemo.service.PatientService;
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
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<Patient> fetchAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public Patient registerNewPatient(@RequestBody Patient patient) {
        return patientService.addNewPatient(patient);
    }

    @DeleteMapping(path = "{patientId}")
    public void deletePatient(@PathVariable("patientId") String id) {
        patientService.deletePatientById(id);
    }

    @PutMapping(path = "{patientId}")
    public Patient updatePatient(@PathVariable("patientId") String id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String email) {
        return patientService.updatePatient(id, name, email);
    }


}
