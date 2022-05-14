package com.fullcycle.catalogovideo.usecase.category.delete;

import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RemoveCategoryUseCase implements IRemoveCategoryUseCase {

    private ICategoryRepository categoryRepository;

    @Override
    public void execute(UUID id) {
        categoryRepository.remove(id);
    }
}
