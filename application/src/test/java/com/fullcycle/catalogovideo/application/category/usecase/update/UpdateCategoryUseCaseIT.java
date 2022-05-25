package com.fullcycle.catalogovideo.application.category.usecase.update;

import com.fullcycle.catalogovideo.application.IntegrationTest;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.category.CategoryMySQLRepository;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@IntegrationTest
public class UpdateCategoryUseCaseIT {


    @Autowired
    private UpdateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    void executReturnsUpdateCategory() {
        Category category = new Category("Action", "Action Description", true);

        Category expected = new Category("Action 2", "Description", true);

        assertEquals(0, repository.count());

        save(category);

        assertEquals(1, repository.count());

        UpdateCategoryInputData inputData = new UpdateCategoryInputData();
        inputData.setId(category.getId().getValue());
        inputData.setName("Action 2");
        inputData.setDescription("Description");
        inputData.setIsActive(category.getIsActive());

        final var actualOutput = useCase.execute(inputData).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.getId());

        final var actualCategory = repository.findById(actualOutput.getId()).get();

        assertEquals(expected.getName(), actualCategory.getName());
        assertEquals(expected.getDescription(), actualCategory.getDescription());
        assertEquals(expected.isIsActive(), actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertTrue(category.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));

    }

    @Test
    void throwNotFoundExceptionWhenIdIsInvalid() {
        Category category = new Category("Action", "Action Description", true);
        UpdateCategoryInputData inputData = new UpdateCategoryInputData();
        inputData.setName("Action 2");
        inputData.setDescription("Description");
        inputData.setIsActive(category.getIsActive());
        inputData.setId(category.getId().getValue());

        assertThrows(NotFoundException.class, () -> useCase.execute(inputData));
    }

    private void save(Category category) {
        repository.saveAndFlush(CategoryPersistence.from(category));
    }


}
