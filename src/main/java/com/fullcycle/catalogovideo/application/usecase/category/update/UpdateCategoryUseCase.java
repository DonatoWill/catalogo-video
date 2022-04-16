package com.fullcycle.catalogovideo.application.usecase.category.update;

import com.fullcycle.catalogovideo.application.exception.NotFoundException;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateCategoryUseCase implements IUpdateCategoryUseCase{

    private ICategoryRepository repository;

    @Override
    public void execute(UUID id, UpdateCategoryInputData inputData) {
        var category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category %s not found", id));

        category.update(
                inputData.getName(),
                inputData.getDescription(),
                inputData.getIsActive()
        );
        repository.update(category);
    }

}
