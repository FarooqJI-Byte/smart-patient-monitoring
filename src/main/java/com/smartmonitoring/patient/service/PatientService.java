package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Constructor Injection (BEST PRACTICE)
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Add new patient
    public Patient addPatient(Patient patient) {
        // Contact number is NOT unique (family / emergency contact)
        patient.setAdmittedDate(LocalDate.now());
        return patientRepository.save(patient);
    }

    // Fetch patient by ID
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }
}
