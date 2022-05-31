package com.fullcycle.catalogovideo.controller.category;


import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryOutput;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryOutput;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class CategoryController implements ICategoryController {

    private AbstractCreateCategoryUseCase createUseCase;
    private IFindByIdCategoryUseCase findByIdUseCase;
    private IUpdateCategoryUseCase updateUseCase;
    private IRemoveCategoryUseCase removeUseCase;
    private IFindAllCategoryUseCase findAllUseCase;

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryInputData input) {

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.getId())).body(output);

        return createUseCase.execute(input)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryOutputData> findAll(CategorySearchQuery query) {
        return findAllUseCase.execute(query);
    }

    @Override
    public CategoryOutputData findById(String id) {
        return findByIdUseCase.execute(id);
    }

    @Override
    public void deleteById(String id) {
        removeUseCase.execute(id);
    }

    @Override
    public ResponseEntity<?> update(String id, UpdateCategoryInputData input) {
        input.setId(id);

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return updateUseCase.execute(input)
                .fold(onError, onSuccess);
    }
}
