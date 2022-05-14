package com.fullcycle.catalogovideo.usecase.category.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryInputData {
    private String name;
    private String description;
    private Boolean isActive;
}
