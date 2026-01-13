package com.smartmonitoring.patient.exception;

import com.smartmonitoring.patient.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(PatientNotFoundException.class)
        public ResponseEntity<ResponseStructure<String>> handlePatientNotFound(
                PatientNotFoundException ex) {

            ResponseStructure<String> response = new ResponseStructure<>();
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(ex.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(NoDataFoundException.class)
        public ResponseEntity<ResponseStructure<String>> handleNoData(
                NoDataFoundException ex) {

            ResponseStructure<String> response = new ResponseStructure<>();
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage(ex.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ResponseStructure<String>> handleBadRequest(
                IllegalArgumentException ex) {

            ResponseStructure<String> response = new ResponseStructure<>();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(ex.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 🚨 ONLY for real bugs
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ResponseStructure<String>> handleServerError(
                Exception ex) {

            ResponseStructure<String> response = new ResponseStructure<>();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal server error. Please contact support.");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

