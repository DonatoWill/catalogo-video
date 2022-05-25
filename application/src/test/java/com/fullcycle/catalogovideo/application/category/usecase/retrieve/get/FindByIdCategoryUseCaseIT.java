package com.fullcycle.catalogovideo.application.category.usecase.retrieve.get;


import com.fullcycle.catalogovideo.application.IntegrationTest;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.get.FindByIdCategoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@IntegrationTest
public class FindByIdCategoryUseCaseIT {

    @Autowired
    private FindByIdCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    void executeReturnsFindCategoryById() {
        Category category =  new Category(
                "Action",
                "Action Description",
                true
        );
        save(category);
        final var actualOutput = useCase.execute(category.getId().getValue());
        assertNotNull(actualOutput);
        assertEquals(category.getName(),actualOutput.getName());
        assertEquals(category.getDescription(), actualOutput.getDescription());
    }

    private void save(Category category) {
        repository.saveAndFlush(CategoryPersistence.from(category));
    }


}
