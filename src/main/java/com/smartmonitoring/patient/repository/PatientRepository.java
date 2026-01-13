package com.smartmonitoring.patient.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmonitoring.patient.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	Optional<Patient> findByContactNumber(String contactNumber);
	List<Patient> findByDoctorId(Long doctorId);


}
