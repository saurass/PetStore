package com.petpic.petpicserv.controller.advice;

import com.petpic.petpicserv.engine.exception.FileEngineException;
import com.petpic.petpicserv.service.exceptions.StorageFileNotFoundException;
import com.petpic.petpicserv.validator.ApiException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
@RestController
public class CustomExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    ResponseEntity<Object> handleMultipartException(MultipartException ex) throws IOException {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(apiException, HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) throws IOException {
        ApiException apiException = new ApiException("Record Not Found",
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFoundException(StorageFileNotFoundException ex) throws IOException {
        ApiException apiException = new ApiException("File Not Found",
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(FileEngineException.class)
    ResponseEntity<Object> handleConstraintViolationException(FileEngineException ex) throws IOException {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(apiException, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public List<ValidationError> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        System.out.println(constraintViolations);
        return buildValidationErrors(constraintViolations);
    }

    private List<ValidationError> buildValidationErrors(Set<ConstraintViolation<?>> violations) {
        return violations.stream().map(violation -> ValidationError.builder().field(StreamSupport.stream(violation.getPropertyPath().spliterator(), false).reduce((first, second) -> second).orElse(null).toString()).error(violation.getMessage()).build()).collect(Collectors.toList());
    }


}
