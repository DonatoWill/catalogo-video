package com.fullcycle.catalogovideo.usecase.category.update;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.exceptions.NotFoundException;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UpdateCategoryUseCaseTests {

    @InjectMocks
    private UpdateCategoryUseCase useCase;

    @Mock
    private ICategoryRepository repository;

    @BeforeEach
    void initUseCase() {
        useCase = new UpdateCategoryUseCase(repository);
    }

    @Test
    void executReturnsUpdateCategory() {
        Category category = new Category("Action", "Action Description", true);

        Category expected = new Category("Action 2", "Description", true);

        Optional<Category> optional = Optional.of(category);

        when(repository.findById(category.getId())).thenReturn(optional);

        UpdateCategoryInputData inputData = new UpdateCategoryInputData();
        inputData.setId(category.getId().getValue());
        inputData.setName("Action 2");
        inputData.setDescription("Description");
        inputData.setIsActive(category.getIsActive());

        category.update(
                inputData.getName(),
                inputData.getDescription(),
                inputData.getIsActive()
        );

        when(repository.update(any())).thenReturn(category);

        useCase.execute(inputData);

        assertNotNull(category);
        assertNotNull(expected);
        assertEquals(category.getName(), expected.getName());
        assertEquals(category.getDescription(), expected.getDescription());
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
}
