package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.model.*;
import com.smartmonitoring.patient.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert createAlert(Patient patient, SeverityLevel severity) {

        Alert alert = new Alert();
        alert.setPatient(patient);
        alert.setSeverity(severity);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setAcknowledged(false);

        if (severity == SeverityLevel.CRITICAL) {
            alert.setMessage("CRITICAL alert: Immediate medical attention required");
        } else if (severity == SeverityLevel.WARNING) {
            alert.setMessage("Warning alert: Patient vitals outside normal range");
        } else {
            alert.setMessage("Patient vitals are normal");
        }

        return alertRepository.save(alert);
    }
}
