package com.smartmonitoring.patient.model;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Integer age;
    private String gender;
    private String contactNumber;
    private LocalDate admittedDate;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getAdmittedDate() {
        return admittedDate;
    }

    public void setAdmittedDate(LocalDate admittedDate) {
        this.admittedDate = admittedDate;
    }
}
