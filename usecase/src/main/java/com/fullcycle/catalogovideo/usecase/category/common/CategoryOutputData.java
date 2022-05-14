package com.fullcycle.catalogovideo.usecase.category.common;

import com.fullcycle.catalogovideo.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryOutputData {
    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;

    public static CategoryOutputData fromDomain(Category category) {
        return new CategoryOutputData(
                UUID.fromString(category.getId().getValue()),
                category.getName(),
                category.getDescription(),
                category.getIsActive()
        );
    }
}