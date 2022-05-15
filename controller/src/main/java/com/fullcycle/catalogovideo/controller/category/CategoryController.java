package com.fullcycle.catalogovideo.controller.category;


import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.AbstractCreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.IUpdateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CategoryController implements ICategoryController {

    private AbstractCreateCategoryUseCase createUseCase;
    private IFindByIdCategoryUseCase findByIdUseCase;
    private IUpdateCategoryUseCase updateUseCase;
    private IRemoveCategoryUseCase removeUseCase;
    private IFindAllCategoryUseCase findAllUseCase;

    @Override
    public CategoryOutputData createCategory(CreateCategoryInputData input) {
        return null; //createUseCase.execute(input);
    }

    @Override
    public Pagination<CategoryOutputData> findAll(CategorySearchQuery query) {
        //var categories = findAllUseCase.execute();
        return null;
    }

    @Override
    public CategoryOutputData findById(UUID id) {
        return findByIdUseCase.execute(CategoryID.from(id).getValue());
    }

    @Override
    public void deleteById(UUID id) {
        removeUseCase.execute(CategoryID.from(id).getValue());
    }

    @Override
    public void update(String id, UpdateCategoryInputData input) {
        input.setId(id);
        //updateUseCase.execute(input);
    }
}
