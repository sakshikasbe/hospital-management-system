package com.sakshi1001.CustomException;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(Long id){
        super("could not find patientReport"+id);
    }
}
