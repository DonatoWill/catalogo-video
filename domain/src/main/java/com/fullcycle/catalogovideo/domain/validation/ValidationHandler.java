package com.fullcycle.catalogovideo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    default boolean hasError(){
        return getErrors() != null && !getErrors().isEmpty();
    }

    List<Error> getErrors();

    default Error firstError(){
        return hasError() ? getErrors().get(0) : null;
    }
    interface Validation {
        boolean validate();
    }

}
