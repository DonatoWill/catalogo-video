package com.fullcycle.catalogovideo.domain.entity;


import com.fullcycle.catalogovideo.domain.validation.Error;
import com.fullcycle.catalogovideo.domain.validation.ValidationHandler;
import com.fullcycle.catalogovideo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints(){
        final var name = category.getName();
        if(name == null ) {
            this.validationHandler().append(new Error("Name should not be null"));
            return;
        }
        if(name.trim().isBlank()){
            this.validationHandler().append(new Error("Name should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if(length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH){
            this.validationHandler().append(new Error("Name should have between 3 and 255 characters"));
        }

    }

}
