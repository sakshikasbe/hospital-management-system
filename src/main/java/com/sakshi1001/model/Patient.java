package com.sakshi1001.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Patient {

    private @Id @GeneratedValue Long id;
    private String name;
    private String Disease;

    public Patient(String name, String disease) {
        this.name = name;
        this.Disease = disease;
    }

}