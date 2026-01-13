package com.smartmonitoring.patient.controller;
import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.model.Vitals;
import com.smartmonitoring.patient.service.VitalsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vitals")
public class VitalsController {

    private final VitalsService vitalsService;

    public VitalsController(VitalsService vitalsService) {
        this.vitalsService = vitalsService;
    }

    @GetMapping("/{patientId}/latest")
    public ResponseEntity<ResponseStructure<Vitals>> getLatestVitals(
            @PathVariable Long patientId) {
        return vitalsService.getLatestVitals(patientId);
    }

    @GetMapping("/{patientId}/history")
    public ResponseEntity<ResponseStructure<List<Vitals>>> getVitalsHistory(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "10") int limit) {
        return vitalsService.getVitalsHistory(patientId, limit);
    }

    @GetMapping("/{patientId}/trend")
    public ResponseEntity<ResponseStructure<Map<String, String>>> getVitalsTrend(
            @PathVariable Long patientId) {
        return vitalsService.getVitalsTrend(patientId);
    }
}
