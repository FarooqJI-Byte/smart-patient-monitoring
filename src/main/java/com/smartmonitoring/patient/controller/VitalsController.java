package com.smartmonitoring.patient.controller;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.SeverityLevel;
import com.smartmonitoring.patient.model.Vitals;
import com.smartmonitoring.patient.service.VitalsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vitals")
public class VitalsController {

    private final VitalsService vitalsService;

    public VitalsController(VitalsService vitalsService) {
        this.vitalsService = vitalsService;
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<ResponseStructure<SeverityLevel>> recordVitals(
            @PathVariable Long patientId,
            @RequestBody Vitals vitals) {

        return vitalsService.recordVitals(patientId, vitals);
    }
}
