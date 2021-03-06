package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryOutput {

    private String id;


    public static CreateCategoryOutput from(final Category category){
        return new CreateCategoryOutput(category.getId().getValue());
    }

    public static CreateCategoryOutput from(final String id){
        return new CreateCategoryOutput(id);
    }

}
