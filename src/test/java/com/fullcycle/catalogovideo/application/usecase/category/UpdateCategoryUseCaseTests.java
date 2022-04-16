package com.fullcycle.catalogovideo.application.usecase.category;

import com.fullcycle.catalogovideo.application.usecase.category.delete.RemoveCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.update.UpdateCategoryInputData;
import com.fullcycle.catalogovideo.application.usecase.category.update.UpdateCategoryUseCase;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UpdateCategoryUseCaseTests {

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
        inputData.setName("Action 2");
        inputData.setDescription("Description");
        inputData.setIsActive(category.getIsActive());

        category.update(
                inputData.getName(),
                inputData.getDescription(),
                inputData.getIsActive()
        );

        doNothing().when(repository).update(category);

        useCase.execute(category.getId(), inputData);

        assertNotNull(category);
        assertNotNull(expected);
        assertEquals(category.getName(), expected.getName());

    }

}