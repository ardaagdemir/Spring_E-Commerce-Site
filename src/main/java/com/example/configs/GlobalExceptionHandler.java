package com.example.configs;

import com.example.services.utils.REnum;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String item;
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, TransactionSystemException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class, MalformedJwtException.class} )
    protected ResponseEntity myFnc( Exception ex ) {
        if ( ex instanceof IllegalStateException ) {
            IllegalStateException il = (IllegalStateException) ex;
            System.out.println( il.getMessage() );
        }
        if(ex instanceof ConstraintViolationException){

            Map<REnum, Object> hm = new LinkedHashMap<>();
            Set<ConstraintViolation<?>> errors = ((ConstraintViolationException) ex).getConstraintViolations();

            List<Map<String ,String >> lss = new ArrayList<>();
            for ( ConstraintViolation<?> item : errors ) {
                Map<String , String > hmx = new HashMap<>();
                String path = String.valueOf(item.getPropertyPath());
                int indexOf =path.indexOf(".");
                String fieldName=path.substring(indexOf +1);
                String message = item.getMessage();
                hmx.put(fieldName, message);
                System.out.println( fieldName + " " + message );
                lss.add(hmx);
            }
            hm.put(REnum.status, false);
            hm.put(REnum.error, lss);
            return new ResponseEntity<>(hm, BAD_REQUEST);
        }
        if(ex instanceof DataIntegrityViolationException){
            Map<REnum, Object> hm = new LinkedHashMap<>();
            String exMessage=ex.getMessage();
            hm.put(REnum.status,false);
            hm.put(REnum.message,exMessage);
            return new ResponseEntity<>(hm, BAD_REQUEST);

        }

        return null;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<REnum, Object> hm = new LinkedHashMap<>();
        List<FieldError> errors = ex.getFieldErrors();
        List<Map<String ,String >> lss = new ArrayList<>();
        for ( FieldError item : errors ) {
            Map<String , String > hmx = new HashMap<>();
            String fieldName = item.getField();
            String message = item.getDefaultMessage();
            hmx.put(fieldName, message);
            System.out.println( fieldName + " " + message );
            lss.add(hmx);
        }
        hm.put(REnum.status, false);
        hm.put(REnum.error, lss);
        return new ResponseEntity<>(hm, BAD_REQUEST);
    }

}