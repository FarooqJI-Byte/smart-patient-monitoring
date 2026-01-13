package com.smartmonitoring.patient.controller;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Alert;
import com.smartmonitoring.patient.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Get all alerts for a patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ResponseStructure<List<Alert>>> getAlertsByPatient(
            @PathVariable Long patientId) {

        return alertService.getAlertsByPatient(patientId);
    }
}
