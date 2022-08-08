package com.example.mongodemo.controller;

import com.example.mongodemo.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.example.mongodemo.TestUtils.createPatient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PatientControllerIntegrationTest {

    @Autowired
    PatientController patientController;

    @Test
    public void getAll() {
        Patient patientStefa = createPatient("Stefa");
        Patient patientSam = createPatient("Sam");

        patientController.registerNewPatient(patientStefa);
        patientController.registerNewPatient(patientSam);

        List<Patient> patients = patientController.fetchAllPatients();
        assertThat(patients).hasSize(2);
        assertThat(patients).containsOnly(patientStefa, patientSam);
    }

    @Test
    public void registerNewPatient() {
        Patient patientStefa = createPatient("Stefa");
        patientController.registerNewPatient(patientStefa);

        assertThat(patientController.fetchAllPatients()).contains(patientStefa);
    }

    @Test
    public void registerNewPatientWithSameEmail() {
        Patient patientStefa = createPatient("Stefa");
        patientController.registerNewPatient(patientStefa);

        assertThat(assertThrows(IllegalStateException.class, () -> patientController.registerNewPatient(patientStefa))).hasMessageContaining(patientStefa.getEmail());
    }

    @Test
    public void deletePatient() {
        Patient patientStefa = createPatient("Stefa");
        Patient patientSam = createPatient("Sam");

        patientController.registerNewPatient(patientStefa);
        patientController.registerNewPatient(patientSam);

        List<Patient> patients = patientController.fetchAllPatients();
        assertThat(patients).hasSize(2);
        assertThat(patients).containsOnly(patientStefa, patientSam);

        patientController.deletePatient(patientStefa.getId());

        patients = patientController.fetchAllPatients();
        assertThat(patients).hasSize(1);
        assertThat(patients).doesNotContain(patientStefa);
    }

    @Test
    public void cantDeleteNotExistedPatient() {
        String dummyId = "some-dummy-id";
        assertThat(assertThrows(IllegalStateException.class, () -> patientController.deletePatient(dummyId))).hasMessageContaining(dummyId);
    }

    @Test
    public void updatePatientWithOccupiedEmail() {
        Patient patientStefa = createPatient("Stefa");
        Patient patientSam = createPatient("Sam");

        patientController.registerNewPatient(patientStefa);
        patientController.registerNewPatient(patientSam);

        assertThat(
                assertThrows(IllegalStateException.class,
                        () -> patientController.updatePatient(patientSam.getId(), "newName", patientStefa.getEmail())))
                .hasMessageContaining(patientStefa.getEmail());
    }

    @Test
    public void updatePatient() {
        Patient patientSam = createPatient("Sam");
        patientController.registerNewPatient(patientSam);

        List<Patient> patients = patientController.fetchAllPatients();
        assertThat(patients).hasSize(1);

        String newName = "Sammy";
        String newEmail = "sammy@gmail.com";

        patientController.updatePatient(patientSam.getId(), newName, newEmail);

        patients = patientController.fetchAllPatients();
        assertThat(patients).hasSize(1);

        Patient updatedPatient = patients.get(0);

        assertThat(updatedPatient.getEmail()).isEqualTo(newEmail);
        assertThat(updatedPatient.getFirstName()).isEqualTo(newName);
        assertThat(updatedPatient.getLastName()).isEqualTo(patientSam.getLastName());
    }

}