package com.fullcycle.catalogovideo.domain.entity;


import com.fullcycle.catalogovideo.domain.validation.Error;
import com.fullcycle.catalogovideo.domain.validation.ValidationHandler;
import com.fullcycle.catalogovideo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if(this.category.getName() == null || this.category.getName().isEmpty()) {
            this.validationHandler().append(new Error("Name should not be null"));
        }
    }


}
