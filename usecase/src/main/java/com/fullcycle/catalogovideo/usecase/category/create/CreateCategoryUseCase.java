package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateCategoryUseCase implements ICreateCategoryUseCase {

    private ICategoryRepository repository;

    @Override
    public CategoryOutputData execute(CreateCategoryInputData inputData) {
        var created = repository.create(toDomain(inputData));
        return CategoryOutputData.fromDomain(created);
    }

    private Category toDomain(CreateCategoryInputData inputData) {
        return new Category(inputData.getName(), inputData.getDescription(), inputData.getIsActive());
    }


}
