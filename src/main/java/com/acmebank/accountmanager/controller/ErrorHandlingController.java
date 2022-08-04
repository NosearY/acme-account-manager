package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.dto.response.ApiErrorDto;
import com.acmebank.accountmanager.exception.InvalidOperationException;
import com.acmebank.accountmanager.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Malformed http request detected");
        return buildResponseEntity(ApiErrorDto.clientSideError());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponseEntity(ApiErrorDto.notFound());
    }

    @ExceptionHandler(InvalidOperationException.class)
    protected ResponseEntity<Object> handleInvalidOperationException(InvalidOperationException ex) {
        return buildResponseEntity(ApiErrorDto.clientSideError(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(ApiErrorDto.serverSideError());
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorDto apiErrorDto) {
        return ResponseEntity.status(apiErrorDto.getStatus()).contentType(MediaType.APPLICATION_JSON).body(apiErrorDto);
    }

}
