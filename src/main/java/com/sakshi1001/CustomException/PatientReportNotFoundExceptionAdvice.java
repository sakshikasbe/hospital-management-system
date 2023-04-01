package com.sakshi1001.CustomException;


import com.sakshi1001.Controller.PatientController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice

public class PatientReportNotFoundExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(PatientReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String patientReportNotFoundhandler (PatientReportNotFoundException ex)
    {
        return ex.getMessage();
    }

    
}
