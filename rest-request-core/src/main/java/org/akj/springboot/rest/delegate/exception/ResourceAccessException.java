package org.akj.springboot.rest.delegate.exception;

public class ResourceAccessException extends RuntimeException {
    public ResourceAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ResourceAccessException(Throwable throwable) {
        super(throwable);
    }

    public ResourceAccessException(String message) {
        super(message);
    }
}
