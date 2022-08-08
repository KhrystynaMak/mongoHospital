package com.example.mongodemo.controller;

import com.example.mongodemo.model.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.example.mongodemo.TestUtils.assertPerson;
import static com.example.mongodemo.TestUtils.createDoctor;
import static com.example.mongodemo.model.Specialization.ORTHOPAEDIST;
import static com.example.mongodemo.model.Specialization.SURGEON;
import static com.example.mongodemo.model.Specialization.THERAPIST;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DoctorControllerIntegrationTest {

    @Autowired
    DoctorController doctorController;

    @Test
    public void save() {
        Doctor doctor = createDoctor();
        doctor.setSpecializations(asList(ORTHOPAEDIST, SURGEON));

        doctorController.registerNewDoctor(doctor);

        assertThat(doctorController.getDoctors(List.of(SURGEON, ORTHOPAEDIST))).containsOnly(doctor);
        assertThat(doctorController.getDoctors(List.of(ORTHOPAEDIST))).containsOnly(doctor);
        assertThat(doctorController.getDoctors(List.of(SURGEON))).containsOnly(doctor);
        assertThat(doctorController.getDoctors(List.of(THERAPIST))).isEmpty();
    }

    @Test
    public void fetchById() {
        Doctor doctor = createDoctor();

        Doctor savedDoctor = doctorController.registerNewDoctor(doctor);

        assertDoctor(doctorController.fetchById(savedDoctor.getId()), savedDoctor);
    }

    private void assertDoctor(Doctor actual, Doctor expected) {
        assertThat(actual.getSpecializations()).containsAll(expected.getSpecializations());
        assertPerson(actual, expected);
    }

    @Test
    public void fetchByNotExistedId() {
        String dummyId = "some-dummy-id";
        assertThat(assertThrows(IllegalStateException.class, () -> doctorController.fetchById(dummyId))).hasMessageContaining(dummyId);
    }

    @Test
    public void getAllDoctors() {
        Doctor doctorTom = createDoctor();
        doctorTom.setFirstName("Tom");
        Doctor doctorJack = createDoctor();
        doctorJack.setFirstName("Jack");
        doctorController.registerNewDoctor(doctorJack);
        doctorController.registerNewDoctor(doctorTom);

        assertThat(doctorController.getDoctors(emptyList())).contains(doctorJack, doctorTom);
    }

    @Test
    public void getAllTherapistsOnly() {
        Doctor doctorTom = createDoctor(asList(THERAPIST, ORTHOPAEDIST));
        doctorTom.setFirstName("Tom");
        Doctor doctorJack = createDoctor(asList(SURGEON, THERAPIST));
        doctorJack.setFirstName("Jack");
        Doctor notTherapist = createDoctor(singletonList(SURGEON));
        doctorController.registerNewDoctor(doctorJack);
        doctorController.registerNewDoctor(doctorTom);
        doctorController.registerNewDoctor(notTherapist);

        List<Doctor> doctors = doctorController.getDoctors(singletonList(THERAPIST));

        assertThat(doctors).contains(doctorJack, doctorTom);
        assertThat(doctors).doesNotContain(notTherapist);
    }

    @Test
    public void deleteDoctor() {
        Doctor doctorTom = createDoctor(asList(THERAPIST, ORTHOPAEDIST));
        Doctor doctorJack = createDoctor(asList(SURGEON, THERAPIST));
        doctorController.registerNewDoctor(doctorJack);
        doctorController.registerNewDoctor(doctorTom);

        assertThat(doctorController.getDoctors(emptyList())).hasSize(2);

        doctorController.deleteDoctor(doctorJack.getId());
        List<Doctor> doctors = doctorController.getDoctors(emptyList());

        assertThat(doctors).hasSize(1);
        assertThat(doctors).containsOnly(doctorTom);
    }

    @Test
    public void cantDeleteNotExistedDoctor() {
        String dummyId = "some-dummy-id";
        assertThat(assertThrows(IllegalStateException.class, () -> doctorController.deleteDoctor(dummyId))).hasMessageContaining(dummyId);
    }

    @Test
    public void addSpecialization() {
        Doctor doctor = createDoctor(singletonList(SURGEON));
        doctorController.registerNewDoctor(doctor);

        assertThat(doctorController.getDoctors(singletonList(THERAPIST))).isEmpty();

        doctorController.addDoctorSpecialization(doctor.getId(), singletonList(THERAPIST));

        assertThat(doctorController.getDoctors(singletonList(THERAPIST))).containsOnly(doctor);
    }
}