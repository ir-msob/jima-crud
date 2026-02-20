package ir.msob.jima.crud.api.restful.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class DebugAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handle(WebExchangeBindException ex) {
        ex.getFieldErrors().forEach(error ->
                System.out.println(error.getField() + " -> " + error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}