package com.fullcycle.catalogovideo.usecase.category.findAll;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.findall.FindAllCategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        CategorySearchQuery query =  CategorySearchQuery.builder()
                .direction("asc")
                .page(0)
                .perPage(10)
                .sort("name")
                .build();

        final var pagination = new Pagination<>(0, 10, categories.size(), categories);

        //Para @Mock os dois funcionam igualmente
        //Usando when com @Spy o método é chamado, assim se houver alguma exception precisa ser tratada
        when(repository.findAll()).thenReturn(pagination);

        //Usando doReturn com @Spy não chama o método do use case
        //doReturn(categories).when(repository).findAll();

        Pagination<CategoryOutputData> actual = useCase.execute(query);

        assertNotNull(actual);
        assertEquals(2, actual.getTotal());
        verify(repository, times(1)).findAll();

        assertThat(actual).isNotNull();
    }

    @Test
    void executeReturnsEmptyList() {
        List<Category> categories = List.of();

        CategorySearchQuery query =  CategorySearchQuery.builder()
                .direction("asc")
                .page(0)
                .perPage(10)
                .sort("name")
                .build();

        final var pagination = new Pagination<>(0, 10, categories.size(), categories);

        when(repository.findAll()).thenReturn(pagination);

        Pagination<CategoryOutputData> actual = useCase.execute(query);

        assertNotNull(categories);
        assertEquals(0, actual.getTotal());
        verify(repository, times(1)).findAll();

        assertThat(actual).isNotNull();
    }

}
