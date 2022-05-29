package com.fullcycle.catalogovideo.usecase.category.update;

import com.fullcycle.catalogovideo.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryOutput {

    private String id;


    public static UpdateCategoryOutput from(final Category category){
        return new UpdateCategoryOutput(category.getId().getValue());
    }

    public static UpdateCategoryOutput from(final String id){
        return new UpdateCategoryOutput(id);
    }

}
