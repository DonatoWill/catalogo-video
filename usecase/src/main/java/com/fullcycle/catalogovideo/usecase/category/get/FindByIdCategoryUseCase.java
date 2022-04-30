package com.fullcycle.catalogovideo.usecase.category.get;

import com.fullcycle.catalogovideo.usecase.exception.NotFoundException;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class FindByIdCategoryUseCase implements IFindByIdCategoryUseCase {

    private ICategoryRepository repository;
    @Override
    public CategoryOutputData execute(UUID id) {
        return repository.findById(id)
                .map(CategoryOutputData::fromDomain)
                .orElseThrow(() -> new NotFoundException("Category %s not found", id));
    }
}
