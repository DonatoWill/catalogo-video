package com.fullcycle.catalogovideo.application.usecase.category;

import com.fullcycle.catalogovideo.application.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.application.usecase.category.create.CreateCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.findall.FindAllCategoryUseCase;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FindAllCategoryUseCaseTests {

    @InjectMocks
    private FindAllCategoryUseCase useCase;

    @Mock
    ICategoryRepository repository;

    @BeforeEach
    void initUseCase() {
        useCase = new FindAllCategoryUseCase(repository);
    }

    @Test
    void executeReturnsFindAllCategory() {
        List<Category> categories = List.of(
                new Category(
                        "Action",
                        "Action Description",
                        true
                ),
                new Category(
                        "Adventure",
                        "Adventure Description",
                        true
                )
        );

        //Para @Mock os dois funcionam igualmente
        //Usando when com @Spy o método é chamado, assim se houver alguma exception precisa ser tratada
        when(repository.findAll()).thenReturn(categories);

        //Usando doReturn com @Spy não chama o método do use case
        doReturn(categories).when(repository).findAll();

        List<CategoryOutputData> actual = useCase.execute();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        verify(repository, times(1)).findAll();

        assertThat(actual).isNotNull();
    }
}
