package com.sakshi1001.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class PatientReport {
    private @Id @GeneratedValue Long id;
    private String patientName;
    private Status status;

    public PatientReport(String patientName, Status status) {
        this.patientName = patientName;
        this.status = status;
    }



}
