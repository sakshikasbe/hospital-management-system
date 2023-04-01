package com.sakshi1001.Controller;

import com.sakshi1001.CustomException.PatientReportNotFoundException;
import com.sakshi1001.assemblers.PatientReportModelAssembler;
import com.sakshi1001.model.PatientReport;
import com.sakshi1001.model.Status;
import com.sakshi1001.repository.PatientReportRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PatientReportController {
    private final PatientReportRepository patientReportRepository;
    private final PatientReportModelAssembler patientReportModelAssembler;



    public PatientReportController(PatientReportRepository patientReportRepository, PatientReportModelAssembler patientReportModelAssembler) {
        this.patientReportRepository = patientReportRepository;
        this.patientReportModelAssembler = patientReportModelAssembler;
    }

    @GetMapping("/patients")
    public CollectionModel<EntityModel<PatientReport>> all(){
        List<EntityModel<PatientReport>> paitentReport= patientReportRepository.findAll().stream()
                .map(patientReportModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(paitentReport,
                linkTo(methodOn(PatientReportController.class).all()).withSelfRel());
    }
    @GetMapping("/admissions/{id}")
    public EntityModel<PatientReport>one(@PathVariable Long id){
        PatientReport patientReport = patientReportRepository.findById(id)
                .orElseThrow(()->new PatientReportNotFoundException(id));
        return patientReportModelAssembler.toModel(patientReport);
    }

    @PostMapping("/admissions")
    ResponseEntity<EntityModel<PatientReport>> newPatientReport(@RequestBody PatientReport patientReport){
        patientReport.setStatus(Status.SERIOUS_PATIENT);
        PatientReport newPatientreport=patientReportRepository.save(patientReport);

        return ResponseEntity
                .created(linkTo(methodOn(PatientReportController.class).one(newPatientreport.getId())).toUri())
                .body(patientReportModelAssembler.toModel(newPatientreport));
    }

    @DeleteMapping("/admissions/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id){
        PatientReport patientReport = patientReportRepository.findById(id)
                .orElseThrow(()->new PatientReportNotFoundException(id));
        if(patientReport.getStatus()==Status.SERIOUS_PATIENT){
            patientReport.setStatus(Status.DEATH_PATIENT);
            return ResponseEntity.ok(patientReportModelAssembler.toModel(patientReportRepository.save(patientReport)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE , MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You cant cancel an admission that is in the "+patientReport.getStatus() + "Status"));
    }

    @PutMapping("admission/{id}/complete")
    public ResponseEntity<?>complete(@PathVariable Long id){
        PatientReport patientReport = patientReportRepository.findById(id)
                .orElseThrow(()->new PatientReportNotFoundException(id));
        if(patientReport.getStatus()==Status.SERIOUS_PATIENT){
            patientReport.setStatus(Status.RECOVER_PATIENT);
            return ResponseEntity.ok(patientReportModelAssembler.toModel(patientReportRepository.save(patientReport)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You cant Confirm an  Which is already Complete "+ patientReport.getStatus()+ "Status"));
    }

}

