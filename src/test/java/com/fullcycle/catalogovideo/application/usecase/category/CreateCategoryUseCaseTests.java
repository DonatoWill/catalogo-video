package com.fullcycle.catalogovideo.application.usecase.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fullcycle.catalogovideo.application.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.application.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.application.usecase.category.create.CreateCategoryUseCase;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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


}
