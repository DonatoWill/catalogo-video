package com.fullcycle.catalogovideo.controller.category;


import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.ICreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.IUpdateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CategoryController implements ICategoryController {

    private ICreateCategoryUseCase createUseCase;
    private IFindByIdCategoryUseCase findByIdUseCase;
    private IUpdateCategoryUseCase updateUseCase;
    private IRemoveCategoryUseCase removeUseCase;
    private IFindAllCategoryUseCase findAllUseCase;

    @Override
    public CategoryOutputData createCategory(CreateCategoryInputData input) {
        return createUseCase.execute(input);
    }

    @Override
    public List<CategoryOutputData> findAll() {
        return findAllUseCase.execute();
    }

    @Override
    public CategoryOutputData findById(UUID id) {
        return findByIdUseCase.execute(id);
    }

    @Override
    public void deleteById(UUID id) {
        removeUseCase.execute(id);
    }

    @Override
    public void update(UUID id, UpdateCategoryInputData input) {
         updateUseCase.execute(id, input);
    }
}
