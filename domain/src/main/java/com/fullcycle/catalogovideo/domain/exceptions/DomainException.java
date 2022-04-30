package com.fullcycle.catalogovideo.domain.exceptions;

public class DomainException extends RuntimeException {

    public DomainException(){
        super();
    }

    public DomainException(String message){
        super(message);
    }
    
}
