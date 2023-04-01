package com.sakshi1001.assemblers;

import com.sakshi1001.Controller.PatientController;
import com.sakshi1001.model.Patient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Configuration
public class PatientModelAssembler  implements RepresentationModelAssembler<Patient, EntityModel<Patient>>{
    @Override
    public EntityModel<Patient> toModel(Patient patient){
        return EntityModel.of(patient,
                linkTo(methodOn(PatientController.class).one(patient.getId())).withSelfRel(),
                linkTo(methodOn(PatientController.class).all()).withRel("patient"));


    }
}