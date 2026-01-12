package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.model.*;
import com.smartmonitoring.patient.repository.PatientRepository;
import com.smartmonitoring.patient.repository.VitalsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VitalsService {

    private final VitalsRepository vitalsRepository;
    private final PatientRepository patientRepository;

    public VitalsService(VitalsRepository vitalsRepository,
                         PatientRepository patientRepository) {
        this.vitalsRepository = vitalsRepository;
        this.patientRepository = patientRepository;
    }

    // Record vitals & return severity
    public SeverityLevel recordVitals(Long patientId, Vitals vitals) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        vitals.setPatient(patient);
        vitals.setRecordedAt(LocalDateTime.now());

        vitalsRepository.save(vitals);

        return evaluateSeverity(vitals);
    }

    // RULE ENGINE (Heart of project)
    private SeverityLevel evaluateSeverity(Vitals v) {

        // CRITICAL conditions
        if (v.getHeartRate() < 40 || v.getHeartRate() > 130
                || v.getOxygenLevel() < 85
                || v.getSystolicBP() > 180
                || v.getTemperature() > 39) {

            return SeverityLevel.CRITICAL;
        }

        // WARNING conditions
        if (v.getHeartRate() < 60 || v.getHeartRate() > 100
                || v.getOxygenLevel() < 92
                || v.getSystolicBP() > 140
                || v.getTemperature() > 37.5) {

            return SeverityLevel.WARNING;
        }

        // NORMAL condition
        return SeverityLevel.NORMAL;
    }
}
