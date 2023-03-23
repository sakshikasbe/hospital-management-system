package com.sakshi1001.Controller;

import com.sakshi1001.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class PatientController {

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }
    


}
