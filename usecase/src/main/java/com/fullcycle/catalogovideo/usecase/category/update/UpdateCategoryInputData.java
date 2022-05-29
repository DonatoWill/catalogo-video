package com.fullcycle.catalogovideo.usecase.category.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryInputData {

    @JsonIgnore
    private String id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_active")
    private Boolean isActive;

    public UpdateCategoryInputData(String name, String description, Boolean isActive){
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }
}
