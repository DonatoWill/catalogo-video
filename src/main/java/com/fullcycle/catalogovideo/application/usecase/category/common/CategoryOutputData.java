package com.fullcycle.catalogovideo.application.usecase.category.common;

import java.util.UUID;

import com.fullcycle.catalogovideo.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryOutputData {
    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;

    public static CategoryOutputData fromDomain(Category category) {
        return new CategoryOutputData(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIsActive()
        );
    }
}