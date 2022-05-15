package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        CategoryOutputData actual = useCase.execute(input).get();

        assertThat(actual.getName()).isEqualTo(category.getName());
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

        CategoryOutputData actual = useCase.execute(input).get();
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.isActive()).isTrue();
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

        CategoryOutputData actual = useCase.execute(input).get();
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.isActive()).isFalse();
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

        CategoryOutputData actual = useCase.execute(input).get();
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.isActive()).isTrue();
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

        CategoryOutputData actual = useCase.execute(input).get();
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.isActive()).isTrue();
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
