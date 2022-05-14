package com.fullcycle.catalogovideo.domain.exceptions;

public class NotNullException extends RuntimeException {

    public NotNullException() {
        super();
    }

    public NotNullException(String message) {
        super(message);
    }

}
