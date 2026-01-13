package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Alert;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.model.SeverityLevel;
import com.smartmonitoring.patient.repository.AlertRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * INTERNAL USE
     * Called from VitalsService whenever vitals are recorded
     */
    public Alert createAlert(Patient patient, SeverityLevel severity) {

        Alert alert = new Alert();
        alert.setPatient(patient);
        alert.setSeverity(severity);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setAcknowledged(false);

        // message based on severity
        if (severity == SeverityLevel.CRITICAL) {
            alert.setMessage("CRITICAL alert: Immediate medical attention required");
        } else if (severity == SeverityLevel.WARNING) {
            alert.setMessage("WARNING alert: Patient vitals outside normal range");
        } else {
            alert.setMessage("Vitals are normal");
        }

        return alertRepository.save(alert);
    }

    /**
     * API USE
     * Fetch all alerts for a patient
     */
    public ResponseEntity<ResponseStructure<List<Alert>>> getAlertsByPatient(Long patientId) {

        List<Alert> alerts = alertRepository.findByPatientId(patientId);

        ResponseStructure<List<Alert>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Alerts fetched successfully");
        response.setData(alerts);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<ResponseStructure<Alert>> acknowledgeAlert(Long alertId) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found with id: " + alertId));

        if (alert.isAcknowledged()) {
            throw new IllegalArgumentException("Alert already acknowledged");
        }

        alert.setAcknowledged(true);
        alert.setAcknowledgedAt(LocalDateTime.now());

        Alert saved = alertRepository.save(alert);

        ResponseStructure<Alert> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Alert acknowledged successfully");
        response.setData(saved);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<ResponseStructure<List<Alert>>> getEscalatedAlerts() {

        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);

        List<Alert> escalatedAlerts = alertRepository
                .findBySeverityAndAcknowledgedFalseAndCreatedAtBefore(
                        SeverityLevel.CRITICAL, threshold);

        ResponseStructure<List<Alert>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Escalated alerts fetched successfully");
        response.setData(escalatedAlerts);

        return ResponseEntity.ok(response);
    }


}
