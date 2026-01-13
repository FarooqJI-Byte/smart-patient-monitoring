package com.smartmonitoring.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmonitoring.patient.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
