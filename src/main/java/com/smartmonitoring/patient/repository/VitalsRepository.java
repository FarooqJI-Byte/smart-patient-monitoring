package com.smartmonitoring.patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmonitoring.patient.model.Vitals;

public interface VitalsRepository extends JpaRepository<Vitals, Long> {
	List<Vitals> findByPatientId(Long patientId);
}
