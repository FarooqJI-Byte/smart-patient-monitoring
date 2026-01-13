package com.smartmonitoring.patient.service;

import com.smartmonitoring.patient.dto.ResponseStructure;
import com.smartmonitoring.patient.exception.NoDataFoundException;
import com.smartmonitoring.patient.exception.PatientNotFoundException;
import com.smartmonitoring.patient.model.Vitals;
import com.smartmonitoring.patient.repository.PatientRepository;
import com.smartmonitoring.patient.repository.VitalsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VitalsService {

    private final VitalsRepository vitalsRepository;
    private final PatientRepository patientRepository;

    public VitalsService(VitalsRepository vitalsRepository,
                         PatientRepository patientRepository) {
        this.vitalsRepository = vitalsRepository;
        this.patientRepository = patientRepository;
    }

    // 1️⃣ Latest vitals
    public ResponseEntity<ResponseStructure<Vitals>> getLatestVitals(Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + patientId));

        Vitals latest = vitalsRepository
                .findTopByPatientIdOrderByRecordedAtDesc(patientId);

        if (latest == null) {
            throw new NoDataFoundException("No vitals recorded yet for this patient");
        }

        ResponseStructure<Vitals> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Latest vitals fetched successfully");
        response.setData(latest);

        return ResponseEntity.ok(response);
    }

    // 2️⃣ Vitals history (last N)
    public ResponseEntity<ResponseStructure<List<Vitals>>> getVitalsHistory(
            Long patientId, int limit) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + patientId));

        List<Vitals> history = vitalsRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, PageRequest.of(0, limit));

        if (history.isEmpty()) {
            throw new NoDataFoundException("No vitals history available for this patient");
        }

        ResponseStructure<List<Vitals>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Vitals history fetched successfully");
        response.setData(history);

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Trend summary (based on last 2 records)
    public ResponseEntity<ResponseStructure<Map<String, String>>> getVitalsTrend(Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + patientId));

        List<Vitals> lastTwo = vitalsRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, PageRequest.of(0, 2));

        if (lastTwo.size() < 2) {
            throw new NoDataFoundException("Not enough vitals data to calculate trends");
        }

        Vitals current = lastTwo.get(0);
        Vitals previous = lastTwo.get(1);

        Map<String, String> trends = new HashMap<>();
        trends.put("heartRate", trend(current.getHeartRate(), previous.getHeartRate()));
        trends.put("oxygenLevel", trend(current.getOxygenLevel(), previous.getOxygenLevel()));
        trends.put("temperature", trend(current.getTemperature(), previous.getTemperature()));
        trends.put("bloodPressure",
                trend(current.getSystolicBP(), previous.getSystolicBP()));

        ResponseStructure<Map<String, String>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Vitals trend calculated successfully");
        response.setData(trends);

        return ResponseEntity.ok(response);
    }

    private String trend(Number current, Number previous) {
        if (current.doubleValue() > previous.doubleValue()) return "UP";
        if (current.doubleValue() < previous.doubleValue()) return "DOWN";
        return "STABLE";
    }
}
