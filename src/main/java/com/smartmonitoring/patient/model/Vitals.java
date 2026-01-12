package com.smartmonitoring.patient.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vitals")
public class Vitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer heartRate;
    private Integer systolicBP;
    private Integer diastolicBP;
    private Integer oxygenLevel;
    private Double temperature;

    private LocalDateTime recordedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // ----- Getters & Setters -----

    public Long getId() {
        return id;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getSystolicBP() {
        return systolicBP;
    }

    public void setSystolicBP(Integer systolicBP) {
        this.systolicBP = systolicBP;
    }

    public Integer getDiastolicBP() {
        return diastolicBP;
    }

    public void setDiastolicBP(Integer diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public Integer getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(Integer oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
