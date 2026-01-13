package com.smartmonitoring.patient.controller;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<Patient>> createPatient(
            @RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Patient>> getPatient(
            @PathVariable Long id) {
        return patientService.getPatientById(id);
    }
}
