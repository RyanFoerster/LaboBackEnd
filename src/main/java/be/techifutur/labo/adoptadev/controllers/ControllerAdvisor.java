package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.exceptions.UniqueViolationException;
import be.techifutur.labo.adoptadev.models.dtos.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        HttpServletRequest req = ((ServletWebRequest) request).getRequest();
        Set<Map<String, Object>> errors = new LinkedHashSet<>();

        String uri = req.getRequestURI();
        String method = req.getMethod();

        ex.getGlobalErrors().forEach(error -> {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("message", error.getDefaultMessage());
            errorData.put("type", "global");
            errors.add(errorData);
        });

        ex.getFieldErrors().forEach(error -> {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("message", error.getDefaultMessage());
            errorData.put("type", "field");
            errorData.put("fieldName", error.getField());
            errors.add(errorData);
        });

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorDTO.builder()
                                .uri(uri)
                                .method(method)
                                .errors(errors)
                                .build()

                );


    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ErrorDTO> handle(UniqueViolationException e, HttpServletRequest request) {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        Set<Map<String, Object>> errors = new LinkedHashSet<>();

        e.getFieldsNames().forEach(field -> {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put(field, " should be unique");
            errors.add(errorData);
        });

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorDTO.builder()
                                .uri(uri)
                                .method(method)
                                .errors(errors)
                                .build()
                );

    }

}
