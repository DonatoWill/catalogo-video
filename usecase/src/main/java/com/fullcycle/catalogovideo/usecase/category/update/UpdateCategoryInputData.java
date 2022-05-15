package com.fullcycle.catalogovideo.usecase.category.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryInputData {
    private String id;
    private String name;
    private String description;
    private Boolean isActive;
}
