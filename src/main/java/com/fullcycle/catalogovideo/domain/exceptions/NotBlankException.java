package com.fullcycle.catalogovideo.domain.exceptions;

public class NotBlankException extends DomainException {

    public NotBlankException() {
        super();
    }

    public NotBlankException(String message) {
        super(message);
    }

}
