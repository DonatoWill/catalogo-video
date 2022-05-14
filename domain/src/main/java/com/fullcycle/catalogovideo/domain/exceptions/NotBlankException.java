package com.fullcycle.catalogovideo.domain.exceptions;

public class NotBlankException extends RuntimeException {

    public NotBlankException() {
        super();
    }

    public NotBlankException(String message) {
        super(message);
    }

}
