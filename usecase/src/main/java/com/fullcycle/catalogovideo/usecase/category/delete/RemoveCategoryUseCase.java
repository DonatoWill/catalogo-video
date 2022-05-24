package com.fullcycle.catalogovideo.usecase.category.delete;

import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveCategoryUseCase extends IRemoveCategoryUseCase {

    private ICategoryRepository categoryRepository;

    @Override
    public void execute(String id) {
        categoryRepository.deleteById(CategoryID.from(id));
    }
}
