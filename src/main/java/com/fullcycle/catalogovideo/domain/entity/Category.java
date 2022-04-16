package com.fullcycle.catalogovideo.domain.entity;

import java.util.UUID;

import com.fullcycle.catalogovideo.domain.exceptions.NotBlankException;
import com.fullcycle.catalogovideo.domain.exceptions.NotNullException;

public class Category {

    private UUID id;
    private String name;
    private String description;
    private Boolean isActive = true;

    public Category(UUID id, String name, String description) {
        this.id = id;
        this.setName(name);
        this.setDescription(description);
    }
    
    public Category(String name, String description, Boolean isActive) {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
    }

    public Category(String name, String description) {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setDescription(description);
    }

    public Category(UUID id, String name, String description, Boolean isActive) {
        this.id = id;
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name == null ){
            throw new NotNullException("Name cannot be null empty");
        }
        if(name.trim().isEmpty()){
            throw new NotBlankException("Name cannot be empty");
        }
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

    public Boolean deactivate(){
        return this.isActive = false;
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