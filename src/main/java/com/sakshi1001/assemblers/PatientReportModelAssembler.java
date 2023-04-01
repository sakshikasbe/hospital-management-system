package com.sakshi1001.assemblers;

import com.sakshi1001.Controller.PatientController;
import com.sakshi1001.model.PatientReport;
import com.sakshi1001.model.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PatientReportModelAssembler implements RepresentationModelAssembler<PatientReport, EntityModel<PatientReport>> {

    @Override
    public  EntityModel<PatientReport>toModel(PatientReport patientReport){

        EntityModel<PatientReport> patientModel = EntityModel.of(patientReport,
                linkTo(methodOn(PatientController.class).one(patientReport.getId())).withSelfRel(),
                linkTo(methodOn(PatientController.class).all()).withRel("all_PatientReport"));

        if(patientReport.getStatus()== Status.DEATH_PATIENT){
            patientModel.add(linkTo(methodOn(PatientController.class).(patientReport.getId())).withRel("cancel"));
            patientModel.add(linkTo(methodOn(PatientController.class).(patientReport.getId())).withRel("complete"));

        }
        return patientModel;
    }
}
