package com.saida.akart_task.error;

import com.saida.akart_task.config.MessageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageGenerator messageGenerator;

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorResponse handleDataIntegrityViolation(ServiceException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("service exception: {}", ex.getMessage());
        return new RestErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value(),uuid);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestErrorResponse handleException(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("Internal server error, uuid: {}, message: {}", uuid, ex.getMessage());
        return new RestErrorResponse(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.value(), uuid);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = messageGenerator.getMessage(ErrorMessage.DUPLICATE_USERNAME);
        String uuid = UUID.randomUUID().toString();
        log.error("DataIntegrityViolationException exception: {}", ex.getMessage());
        return new RestErrorResponse(errorMessage,HttpStatus.BAD_REQUEST.value(),uuid);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Type MisMatch exception:{}", ex.getMessage());
        RestErrorResponse response =
                new RestErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(),uuid);

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Method not supported,exception:{}", ex.getMessage());
        RestErrorResponse response =
                new RestErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.METHOD_NOT_ALLOWED.value(),uuid);
        return new ResponseEntity<>(response, headers, HttpStatus.METHOD_NOT_ALLOWED);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Http Message Not Readable Exception, message:{}", ex.getMessage());
        String errorMessage = ex.getMessage();
        RestErrorResponse response =
                new RestErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value(),uuid );
        return new ResponseEntity<>(response, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        Set<String> errorMessages = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = null;
            String errorMessage = null;
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
                errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + " " + errorMessage);
            } else if (error != null) {
                fieldName = (error).getObjectName();
                errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + " " + errorMessage);
            }
            log.error("Validation exception fieldName: {} , message: {}", fieldName, errorMessage);
        });
        RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(),uuid);
        return new ResponseEntity<>(response, headers, status);
    }
}