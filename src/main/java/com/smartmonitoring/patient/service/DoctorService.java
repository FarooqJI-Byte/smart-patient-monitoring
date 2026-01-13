package com.smartmonitoring.patient.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.exception.NoDataFoundException;
import com.smartmonitoring.patient.exception.PatientNotFoundException;
import com.smartmonitoring.patient.model.Alert;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.model.SeverityLevel;
import com.smartmonitoring.patient.repository.*;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AlertRepository alertRepository;

    public DoctorService(DoctorRepository doctorRepository,
                         PatientRepository patientRepository,
                         AlertRepository alertRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.alertRepository = alertRepository;
    }

    // 1️⃣ Patients under doctor
    public ResponseEntity<ResponseStructure<List<Patient>>> getDoctorPatients(Long doctorId) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Doctor not found with id: " + doctorId));

        List<Patient> patients = patientRepository.findByDoctorId(doctorId);

        if (patients.isEmpty()) {
            throw new NoDataFoundException("No patients assigned to this doctor");
        }

        ResponseStructure<List<Patient>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Doctor patients fetched successfully");
        response.setData(patients);

        return ResponseEntity.ok(response);
    }

    // 2️⃣ Critical alerts for doctor
    public ResponseEntity<ResponseStructure<List<Alert>>> getCriticalAlerts(Long doctorId) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Doctor not found with id: " + doctorId));

        List<Alert> alerts = alertRepository
                .findByPatientDoctorIdAndSeverity(doctorId, SeverityLevel.CRITICAL);

        if (alerts.isEmpty()) {
            throw new NoDataFoundException("No critical alerts for this doctor");
        }

        ResponseStructure<List<Alert>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Critical alerts fetched successfully");
        response.setData(alerts);

        return ResponseEntity.ok(response);
    }
}
