package com.fullcycle.catalogovideo.domain.exceptions;

import com.fullcycle.catalogovideo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(final String aMessage, List<Error> errors){
        super(aMessage, null);
        this.errors = errors;
    }

    public static DomainException with(Error error){
        return new DomainException(error.getMessage(), List.of(error));
    }

    public static DomainException with(List<Error> errors){
        return new DomainException("", errors);
    }

    public List<Error> getErrors(){
        return errors;
    }
    
}
