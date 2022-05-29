package com.fullcycle.catalogovideo.domain.exceptions;

import com.fullcycle.catalogovideo.domain.AggregateRoot;
import com.fullcycle.catalogovideo.domain.Identifier;
import com.fullcycle.catalogovideo.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<Error> errors) {
        super(aMessage, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ){
        final var anError = "%s with Id %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );

        return new NotFoundException(anError, Collections.emptyList());
    }
}
