package com.fullcycle.catalogovideo.usecase.category;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
            category.active()
        );

        CategoryOutputData actual = useCase.execute(input);
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
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

        CategoryOutputData actual = useCase.execute(input);
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.getIsActive()).isTrue();
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

        CategoryOutputData actual = useCase.execute(input);
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.getIsActive()).isFalse();
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

        CategoryOutputData actual = useCase.execute(input);
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.getIsActive()).isTrue();
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

        CategoryOutputData actual = useCase.execute(input);
        repository.create(category);

        assertThat(actual.getName()).isEqualTo(category.getName());
        assertThat(actual.getIsActive()).isTrue();
    }

}
