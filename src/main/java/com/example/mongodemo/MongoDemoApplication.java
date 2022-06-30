package com.example.mongodemo;

import com.example.mongodemo.model.Address;
import com.example.mongodemo.model.Doctor;
import com.example.mongodemo.model.Patient;
import com.example.mongodemo.model.Specialization;
import com.example.mongodemo.model.Symptoms;
import com.example.mongodemo.model.Vaccination;
import com.example.mongodemo.service.DoctorService;
import com.example.mongodemo.service.PatientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.example.mongodemo.model.Gender.*;
import static com.example.mongodemo.model.Gender.FEMALE;
import static java.util.Arrays.asList;

@SpringBootApplication
public class MongoDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(DoctorService doctorService, PatientService patientService) {
		return args -> {
			/*Address address = new Address("USA", "Chicago", "CHI45");

			Doctor doctor = new Doctor("Alisa", "Shmidt", "alisas@gmail.com", FEMALE, address, LocalDate.now().minusYears(30));
			doctor.setSpecializations(asList(Specialization.ORTHOPAEDIST, Specialization.SURGEON));
			doctor = doctorService.saveDoctor(doctor);

			Vaccination vaccination = new Vaccination("trulala", 1, Set.of(Symptoms.TEMPERATURE, Symptoms.REDNESS), LocalDateTime.now(), doctor.getId());
			Patient patient = new Patient("Tom", "Cruz", "tomc@gmail.com", MALE, address, LocalDate.now().minusYears(50));
			patient.setVaccinations(List.of(vaccination));
			patientService.addNewPatient(patient);*/
		};
	}

}
