package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.model.SeverityLevel;
import com.smartmonitoring.patient.model.Vitals;
import com.smartmonitoring.patient.repository.PatientRepository;
import com.smartmonitoring.patient.repository.VitalsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VitalsService {

    private final VitalsRepository vitalsRepository;
    private final PatientRepository patientRepository;
    private final AlertService alertService;

    public VitalsService(VitalsRepository vitalsRepository,
                         PatientRepository patientRepository,
                         AlertService alertService) {
        this.vitalsRepository = vitalsRepository;
        this.patientRepository = patientRepository;
        this.alertService = alertService;
    }

    public ResponseEntity<ResponseStructure<SeverityLevel>> recordVitals(
            Long patientId, Vitals vitals) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        vitals.setPatient(patient);
        vitals.setRecordedAt(LocalDateTime.now());
        vitalsRepository.save(vitals);

        SeverityLevel severity = evaluateSeverity(vitals);

        // create alert based on severity
        alertService.createAlert(patient, severity);

        ResponseStructure<SeverityLevel> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Vitals recorded successfully. Severity evaluated.");
        response.setData(severity);

        return ResponseEntity.ok(response);
    }

    private SeverityLevel evaluateSeverity(Vitals v) {

        if (v.getHeartRate() < 40 || v.getHeartRate() > 130
                || v.getOxygenLevel() < 85
                || v.getSystolicBP() > 180
                || v.getTemperature() > 39) {
            return SeverityLevel.CRITICAL;
        }

        if (v.getHeartRate() < 60 || v.getHeartRate() > 100
                || v.getOxygenLevel() < 92
                || v.getSystolicBP() > 140
                || v.getTemperature() > 37.5) {
            return SeverityLevel.WARNING;
        }

        return SeverityLevel.NORMAL;
    }
}
