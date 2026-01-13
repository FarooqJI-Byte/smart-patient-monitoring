package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.exception.NoDataFoundException;
import com.smartmonitoring.patient.exception.PatientNotFoundException;
import com.smartmonitoring.patient.model.Alert;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.model.SeverityLevel;
import com.smartmonitoring.patient.repository.AlertRepository;
import com.smartmonitoring.patient.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AlertRepository alertRepository;

    public PatientService(PatientRepository patientRepository,
                          AlertRepository alertRepository) {
        this.patientRepository = patientRepository;
        this.alertRepository = alertRepository;
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
        response.setMessage("Patient admitted successfully");
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
    public ResponseEntity<ResponseStructure<String>> getPatientCurrentStatus(Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + patientId));

        Alert latestAlert =
                alertRepository.findTopByPatientIdOrderByCreatedAtDesc(patientId);

        if (latestAlert == null) {
            throw new NoDataFoundException("No alerts found for this patient");
        }

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Patient current status fetched successfully");
        response.setData(latestAlert.getSeverity().name());

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<ResponseStructure<List<Patient>>> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            throw new NoDataFoundException("No patients available");
        }

        ResponseStructure<List<Patient>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMessage("Patients fetched successfully");
        response.setData(patients);

        return ResponseEntity.ok(response);
    }



}
