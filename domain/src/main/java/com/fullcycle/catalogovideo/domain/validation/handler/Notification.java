package com.fullcycle.catalogovideo.domain.validation.handler;

import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.domain.validation.Error;
import com.fullcycle.catalogovideo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error error) {
        return new Notification(new ArrayList<>()).append(error);
    }

    public static Notification create(final Throwable error) {
        return new Notification(new ArrayList<>()).append(new Error(error.getMessage()));
    }
    @Override
    public Notification append(Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(Validation aValidation) {
        try{
            aValidation.validate();
        }catch (final DomainException e){
            this.errors.addAll(e.getErrors());
        }catch (final Exception e){
            this.errors.add(new Error(e.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public boolean hasError() {
        return !this.errors.isEmpty();
    }
}
