package com.fullcycle.catalogovideo.usecase.category.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryInputData {

    @JsonIgnore
    private String id;
    private String name;
    private String description;
    private Boolean isActive;
}
