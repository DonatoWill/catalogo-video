package com.fullcycle.catalogovideo.domain.entity;

import com.fullcycle.catalogovideo.domain.AggregateRoot;
import com.fullcycle.catalogovideo.domain.exceptions.NotBlankException;
import com.fullcycle.catalogovideo.domain.exceptions.NotNullException;
import com.fullcycle.catalogovideo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private Boolean isActive = true;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;


    public Category(CategoryID id, String name, String description) {
        super(id);
        this.setName(name);
        this.setDescription(description);
    }
    
    public Category(String name, String description, Boolean isActive) {
        super(CategoryID.unique());
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
    }

    public Category(String name, String description) {
        super(CategoryID.unique());
        this.setName(name);
        this.setDescription(description);
    }

    public Category(UUID id, String name, String description, Boolean isActive) {
        super(CategoryID.from(id));
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public CategoryID getId() {
        return this.id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
//        if(name == null ){
//            throw new NotNullException("Name cannot be null empty");
//        }
//        if(name.trim().isEmpty()){
//            throw new NotBlankException("Name cannot be empty");
//        }
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return this.isActive;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Boolean active(){
        return this.isActive = true;
    }

    public void deactivate(){
        this.isActive = false;
    }

    public void update(String name, String description, Boolean isActive){
        this.setName(name);
        this.setDescription(description);
        if(isActive != null && isActive != this.isActive){
            if(isActive){
                this.active();
            }else{
                this.deactivate();
            }
        }
    }



}