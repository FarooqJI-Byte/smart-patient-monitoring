package com.smartmonitoring.patient.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Alert;
import com.smartmonitoring.patient.model.Patient;
import com.smartmonitoring.patient.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Dashboard → patients
    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<ResponseStructure<List<Patient>>> getDoctorPatients(
            @PathVariable Long doctorId) {

        return doctorService.getDoctorPatients(doctorId);
    }

    // Dashboard → critical alerts
    @GetMapping("/{doctorId}/critical-alerts")
    public ResponseEntity<ResponseStructure<List<Alert>>> getCriticalAlerts(
            @PathVariable Long doctorId) {

        return doctorService.getCriticalAlerts(doctorId);
    }
}
