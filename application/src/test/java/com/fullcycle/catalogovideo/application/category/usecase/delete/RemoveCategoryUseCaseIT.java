package com.fullcycle.catalogovideo.application.category.usecase.delete;

import com.fullcycle.catalogovideo.application.IntegrationTest;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.delete.RemoveCategoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@IntegrationTest
class RemoveCategoryUseCaseIT {

    @Autowired
    private RemoveCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    void givenAValidId_whenCallsDeleteCategory_shouldDelete() {

        Category category =  new Category(
                "Action",
                "Action Description",
                true
        );
        save(category);
        assertEquals(1, repository.count());
        useCase.execute(category.getId().getValue());

        assertNotNull(category);
        assertEquals(0, repository.count());
    }


    private void save(Category category) {
        repository.saveAndFlush(CategoryPersistence.from(category));
    }
}
