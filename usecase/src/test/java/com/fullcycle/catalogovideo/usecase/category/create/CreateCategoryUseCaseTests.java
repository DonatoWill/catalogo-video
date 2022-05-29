package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CreateCategoryUseCaseTests {

    @InjectMocks
    private CreateCategoryUseCase useCase;

    @Mock
    ICategoryRepository repository;

    @BeforeEach
    void initUseCase() {
        useCase = new CreateCategoryUseCase(repository);
    }

    @Test
    void executeReturnsCreatedCategory() {
        Category category = new Category("Action", "Action Category");

        when(repository.create(any(Category.class))).thenReturn(category);
        
        CreateCategoryInputData input = new CreateCategoryInputData(
            category.getName(),
            category.getDescription(),
            category.isIsActive()
        );

        CreateCategoryOutput actual = useCase.execute(input).get();
        assertNotNull(actual.getId());

    }

    @Test
    void executeReturnsCreatedCategoryWithActiveTrue() {
        Category category = new Category("Action", "Action Category");

        when(repository.create(any(Category.class))).thenReturn(category);

        CreateCategoryInputData input = new CreateCategoryInputData(
            category.getName(),
            category.getDescription(),
            true
        );

        CreateCategoryOutput actual = useCase.execute(input).get();
        repository.create(category);
        assertNotNull(actual.getId());
    }

    @Test
    void executeReturnsCreatedCategoryWithActiveFalse() {
        Category category = new Category("Action", "Action Category", false);

        when(repository.create(any(Category.class))).thenReturn(category);

        CreateCategoryInputData input = new CreateCategoryInputData(
            category.getName(),
            category.getDescription(),
            false
        );

        CreateCategoryOutput actual = useCase.execute(input).get();
        repository.create(category);

        assertNotNull(actual.getId());
    }

    @Test
    void executeReturnsCreatedCategoryWithActiveTrueAndDescriptionNull() {
        Category category = new Category("Action", "Action Category");

        when(repository.create(any(Category.class))).thenReturn(category);

        CreateCategoryInputData input = new CreateCategoryInputData(
            category.getName(),
            null,
            true
        );

        CreateCategoryOutput actual = useCase.execute(input).get();
        repository.create(category);

        assertNotNull(actual.getId());
    }

    @Test
    void executeReturnsCreatedCategoryWithActiveTrueAndDescriptionEmpty() {
        Category category = new Category("Action", "Action Category");

        when(repository.create(any(Category.class))).thenReturn(category);

        CreateCategoryInputData input = new CreateCategoryInputData(
            category.getName(),
            "",
            true
        );

        CreateCategoryOutput actual = useCase.execute(input).get();
        repository.create(category);

        assertNotNull(actual.getId());
    }

    @Test
    void givenInvalidName_whenCallCreateCategory_thenThrowException() {
        CreateCategoryInputData input = new CreateCategoryInputData(
            "",
            "Action Category",
            true
        );

        when(repository.create(any(Category.class))).thenAnswer(returnsFirstArg());

        final var notification = useCase.execute(input).getLeft();

        assertEquals("Name should not be empty", notification.firstError().getMessage());
        verify(repository, times(0)).create(any(Category.class));
    }

}
