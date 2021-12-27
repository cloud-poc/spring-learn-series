package org.akj.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<BaseResponse> handleBindException(WebExchangeBindException e) {
        BaseResponse response = BaseResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                .message(getExceptionString(e))
                .build();
        return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<BaseResponse> handleWebInputException(ServerWebInputException ex) {
        BaseResponse response = BaseResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
    }

    private String getExceptionString(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
}
