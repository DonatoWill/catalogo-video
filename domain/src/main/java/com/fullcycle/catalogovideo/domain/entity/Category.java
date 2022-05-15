package com.fullcycle.catalogovideo.domain.entity;

import com.fullcycle.catalogovideo.domain.AggregateRoot;
import com.fullcycle.catalogovideo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean isActive = true;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;


    public Category(CategoryID id, String name, String description) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.createdAt = Instant.now();
        this.deletedAt = isActive? null : Instant.now();
    }

    public static Category newCategory(String name, String description, boolean isActive){
        return new Category(name, description, isActive);
    }

    public static Category newCategory(String id, String name, String description, boolean isActive, Instant createdAt, Instant updatedAt, Instant deletedAt){
        return new Category(CategoryID.from(id), name, description, isActive, createdAt, updatedAt, deletedAt);
    }

    public Category(CategoryID id, String name, String description, boolean isActive, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.deletedAt = deletedAt;
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public Category(String name, String description, boolean isActive) {
        super(CategoryID.unique());
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
        this.createdAt = Instant.now();
        this.deletedAt = isActive? null : Instant.now();
        this.updatedAt = Instant.now();
    }

    public Category(String name, String description) {
        super(CategoryID.unique());
        this.setName(name);
        this.setDescription(description);
        this.createdAt = Instant.now();
        this.deletedAt = isActive? null : Instant.now();
        this.updatedAt = Instant.now();
    }

    public Category(UUID id, String name, String description, boolean isActive) {
        super(CategoryID.from(id));
        this.setName(name);
        this.setDescription(description);
        this.isActive = isActive;
        this.createdAt = Instant.now();
        this.deletedAt = isActive? null : Instant.now();
        this.updatedAt = Instant.now();
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
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public Category activate(){
        this.deletedAt = null;
        this.isActive = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category deactivate(){
        if(getDeletedAt() == null){
            this.deletedAt = Instant.now();
        }
        this.isActive = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
    public Category update(final String name, final String description, final boolean isActive){
        this.setName(name);
        this.setDescription(description);
        if(isActive != this.isActive){
            if(isActive){
                this.activate();
            }else{
                this.deactivate();
            }
        }
        this.updatedAt = Instant.now();
        return this;
    }

}