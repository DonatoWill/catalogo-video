package com.fullcycle.catalogovideo.usecase.category;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.delete.RemoveCategoryUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RemoveCategoryUseCaseTests {

    @InjectMocks
    private RemoveCategoryUseCase useCase;

    @Mock
    private ICategoryRepository repository;

    @Test
    void removeCategory() {
        Category category =  new Category(
                "Action",
                "Action Description",
                true
        );

        Optional<Category> optionalCategory = Optional.of(category);
        doNothing().when(repository).remove(category.getId());
        useCase.execute(category.getId());
        assertNotNull(category);
        verify(repository, times(1)).remove(category.getId());
    }

    @Test
    void removeCategoryNotFound() {

    }
}
