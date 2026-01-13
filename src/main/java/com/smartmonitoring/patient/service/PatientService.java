package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // CREATE
    public ResponseEntity<ResponseStructure<Patient>> createPatient(Patient patient) {

        // basic validation (can be expanded later)
        if (patient.getFullName() == null || patient.getFullName().isBlank()) {
            throw new IllegalArgumentException("Patient name is mandatory");
        }
        if (patient.getContactNumber() == null || patient.getContactNumber().isBlank()) {
            throw new IllegalArgumentException("Contact number is mandatory");
        }

        // system-managed field
        patient.setAdmittedDate(LocalDate.now());

        Patient saved = patientRepository.save(patient);

        ResponseStructure<Patient> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Patient created successfully");
        response.setData(saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // READ
    public ResponseEntity<ResponseStructure<Patient>> getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        ResponseStructure<Patient> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Patient fetched successfully");
        response.setData(patient);

        return ResponseEntity.ok(response);
    }
}
