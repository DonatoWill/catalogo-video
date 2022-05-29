package com.fullcycle.catalogovideo.usecase.category.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryOutputData {

    private String id;
    private String name;
    private String description;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("updated_at")
    private Instant updatedAt;
    @JsonProperty("deleted_at")
    private Instant deletedAt;

    public static CategoryOutputData fromDomain(Category category) {
        return new CategoryOutputData(
                category.getId().getValue(),
                category.getName(),
                category.getDescription(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }

}