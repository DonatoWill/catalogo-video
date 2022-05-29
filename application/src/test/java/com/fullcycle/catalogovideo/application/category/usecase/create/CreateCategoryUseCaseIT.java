package com.fullcycle.catalogovideo.application.category.usecase.create;

import com.fullcycle.catalogovideo.application.IntegrationTest;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    void executeReturnsCreatedCategory() {
        Category category = new Category("Action", "Action Category");

        final var input = new CreateCategoryInputData(
                category.getName(),
                category.getDescription(),
                category.isIsActive()
        );

        final var actualOutput = useCase.execute(input).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.getId());
        assertEquals(1, repository.count());

        final var actualCategory = repository.findById(actualOutput.getId()).get();

        assertEquals(input.getName(), actualCategory.getName());
        assertEquals(input.getDescription(), actualCategory.getDescription());
        assertNotNull(actualCategory.getCreatedAt());

    }

    @Test
    void executeReturnsCreatedCategoryWithActiveTrue() {
        Category category = new Category("Action", "Action Category");

        CreateCategoryInputData input = new CreateCategoryInputData(
                category.getName(),
                category.getDescription(),
                true
        );

        final var actualOutput = useCase.execute(input).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.getId());
        assertEquals(1, repository.count());


        final var actualCategory = repository.findById(actualOutput.getId()).get();

        assertEquals(actualOutput.getId(), actualCategory.getId());
        assertNotNull(actualCategory.getCreatedAt());
    }


    @Test
    void givenInvalidName_whenCallCreateCategory_thenThrowException() {
        CreateCategoryInputData input = new CreateCategoryInputData(
                "",
                "Action Category",
                true
        );

        final var notification = useCase.execute(input).getLeft();

        assertEquals(1, notification.getErrors().size());
        assertEquals("Name should not be empty", notification.firstError().getMessage());
        assertEquals(0, repository.count());
    }

    @Test
    void givenInvalidNullName_whenCallCreateCategory_thenThrowException() {
        CreateCategoryInputData input = new CreateCategoryInputData(
                null,
                "Action Category",
                true
        );
        final var notification = useCase.execute(input).getLeft();
        assertEquals(1, notification.getErrors().size());
        assertEquals("Name should not be null", notification.firstError().getMessage());
        assertEquals(0, repository.count());
    }

}
