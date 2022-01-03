package org.akj.springboot.rest.delegate.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataAccessException(Throwable throwable) {
        super(throwable);
    }

    public DataAccessException(String message) {
        super(message);
    }
}
