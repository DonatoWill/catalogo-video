package com.fullcycle.catalogovideo.usecase.category.get;

import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.usecase.exception.NotFoundException;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindByIdCategoryUseCase extends IFindByIdCategoryUseCase {

    private ICategoryRepository repository;
    @Override
    public CategoryOutputData execute(String id) {
        return repository.findById(CategoryID.from(id))
                .map(CategoryOutputData::fromDomain)
                .orElseThrow(() -> new NotFoundException("Category %s not found", id));
    }
}
