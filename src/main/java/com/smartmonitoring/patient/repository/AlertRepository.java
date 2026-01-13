package com.smartmonitoring.patient.repository;

import com.smartmonitoring.patient.model.Alert;
import com.smartmonitoring.patient.model.SeverityLevel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByPatientId(Long patientId);
    List<Alert> findBySeverityAndAcknowledgedFalseAndCreatedAtBefore(
            SeverityLevel severity,
            LocalDateTime time);
    Alert findTopByPatientIdOrderByCreatedAtDesc(Long patientId);


}
