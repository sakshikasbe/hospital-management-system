package com.sakshi1001.CustomException;

public class PatientReportNotFoundException extends RuntimeException{
    public PatientReportNotFoundException (Long id){
        super("could not found Patient"+ id);
    }
}
