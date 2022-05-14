package com.fullcycle.catalogovideo.usecase.category;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.get.FindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FindByIdCategoryUseCaseTests {

    @InjectMocks
    private FindByIdCategoryUseCase useCase;

    @Mock
    ICategoryRepository repository;

    @BeforeEach
    void initUseCase() {
        useCase = new FindByIdCategoryUseCase(repository);
    }

    @Test
    void executeReturnsFindAllCategory() {
        Category category =  new Category(
                        "Action",
                        "Action Description",
                        true
        );

        Optional<Category> optionalCategory = Optional.of(category);
        //Para @Mock os dois funcionam igualmente
        //Usando when com @Spy o método é chamado, assim se houver alguma exception precisa ser tratada
        when(repository.findById(UUID.fromString(category.getId().getValue()))).thenReturn(optionalCategory);

        CategoryOutputData actual = useCase.execute(UUID.fromString(category.getId().getValue()));

        assertNotNull(category);
        verify(repository, times(1)).findById(UUID.fromString(category.getId().getValue()));

        assertThat(actual).isNotNull();
    }

    @Test
    void throwNotFoundExceptionWhenIdIsWrong() {
        assertThrows(NotFoundException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}