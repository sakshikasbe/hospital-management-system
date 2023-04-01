package com.sakshi1001.Controller;

import com.sakshi1001.CustomException.PatientNotFoundException;
import com.sakshi1001.assemblers.PatientModelAssembler;
import com.sakshi1001.model.Patient;
import com.sakshi1001.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Slf4j
@RestController

public class PatientController {

    private PatientRepository repository;

    private final PatientModelAssembler patientModelAssembler;
    private PatientModelAssembler PatientModelAssembler;

    public PatientController(PatientRepository repository, com.sakshi1001.assemblers.PatientModelAssembler patientModelAssembler) {
        this.repository = repository;
        this.patientModelAssembler = patientModelAssembler;
    }

    @GetMapping("/patinet")
    public CollectionModel<EntityModel<Patient>> all() {

        List<EntityModel<Patient>> Patient  = (List<EntityModel<Patient>>) repository.findAll()
                .stream()
                .map(patientModelAssembler::toModel)
                .toList();

        return CollectionModel.of(Patient, linkTo(methodOn(PatientController.class).all()).withSelfRel());
    }
    @GetMapping("/patient/{id}")
    public EntityModel<Patient>one(@PathVariable Long id){
        Patient patient = repository.findById(id)
                .orElseThrow(()->new PatientNotFoundException(id));
        return patientModelAssembler.toModel(patient);
    }

    @PostMapping("/patient")
   Patient newPatient(@RequestBody Patient newPatient) {

        return repository.save(newPatient);

    }

    @PutMapping("/patient/{id}")
    ResponseEntity<?> replacePatinet(@RequestBody Patient newPatinet,@PathVariable Long id) {

        Patient updatedPatient = repository.findById(id) //
                .map(patient -> {
                    patient.setName(newPatinet.getName());
                    patient.setDisease(newPatinet.getDisease());
                    return repository.save(patient);
                }) //
                .orElseGet(() -> {
                    newPatinet.setId(id);
                    return repository.save(newPatinet);
                });

        EntityModel<Patient> entityModel = patientModelAssembler.toModel(updatedPatient);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("patient/{id}")
    ResponseEntity<?>deletePatient(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




    }


