package com.example.mongodemo;

import com.example.mongodemo.model.Address;
import com.example.mongodemo.model.Doctor;
import com.example.mongodemo.model.Patient;
import com.example.mongodemo.model.Person;
import com.example.mongodemo.model.Specialization;
import com.example.mongodemo.model.Symptoms;
import com.example.mongodemo.model.Vaccination;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.example.mongodemo.model.Gender.FEMALE;
import static com.example.mongodemo.model.Gender.MALE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
    private TestUtils() {
    }

    public static Address createAddress() {
        return new Address("USA", "Chicago", "CHI45");
    }

    public static Doctor createDoctor() {
        Doctor doctor = new Doctor("Alisa", "Shmidt", "alisas@gmail.com", FEMALE, createAddress(), LocalDate.now().minusYears(30));
        doctor.setSpecializations(asList(Specialization.ORTHOPAEDIST, Specialization.SURGEON));
        return doctor;
    }

    public static Doctor createDoctor(List<Specialization> specializations) {
        Doctor doctor = createDoctor();
        doctor.setSpecializations(specializations);
        return doctor;
    }

    public static Vaccination createVaccination(Doctor doctor) {
        return new Vaccination("trulala", 1, Set.of(Symptoms.TEMPERATURE, Symptoms.REDNESS), LocalDateTime.now(), doctor.getId());
    }

    public static Patient createPatient(String name) {
        Doctor doctor = createDoctor();
        Vaccination vaccination = createVaccination(doctor);
        Patient patient = new Patient(name, "Cruz", name + "@gmail.com", MALE, createAddress(), LocalDate.now().minusYears(50));
        patient.setVaccinations(List.of(vaccination));
        return patient;
    }

    public static void assertPerson(Person actual, Person expected) {
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getBirthDate()).isEqualTo(expected.getBirthDate());
        assertThat(actual.getRegistered()).isEqualTo(expected.getRegistered());
        assertThat(actual.getGender()).isEqualTo(expected.getGender());

        assertAddress(actual.getAddress(), expected.getAddress());
    }

    public static void assertAddress(Address actual, Address expected) {
        assertThat(actual.getCity()).isEqualTo(expected.getCity());
        assertThat(actual.getCountry()).isEqualTo(expected.getCountry());
        assertThat(actual.getPostcode()).isEqualTo(expected.getPostcode());
    }

}
