package com.fullcycle.catalogovideo.usecase.category.delete;

import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveCategoryUseCase extends IRemoveCategoryUseCase {

    private ICategoryRepository categoryRepository;

    @Override
    public void execute(String id) {
        categoryRepository.remove(CategoryID.from(id));
    }
}
