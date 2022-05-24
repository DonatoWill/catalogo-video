package com.fullcycle.catalogovideo.infrastructure.category;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CategoryMySQLRepositoryTest {

    @InjectMocks
    private CategoryMySQLRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        repository = new CategoryMySQLRepository(categoryRepository);
    }

    @Test
    public void saveCategory() {
        Category expected = new Category(
                "Action",
                "Action Description",
                true
        );

        doReturn(CategoryPersistence.from(expected))
                .when(categoryRepository)
                .save(any(CategoryPersistence.class));

        Category actual = repository.create(expected);

        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Action");
        assertTrue(actual.getIsActive());

    }


    @Test
    void findById() {
        Category entity = new Category(
                "Action",
                "Action Description",
                true
        );

        CategoryPersistence expected = CategoryPersistence.from(entity);
        doReturn(Optional.of(expected))
                .when(categoryRepository)
                .findById(any());

        Optional<Category> actual = repository.findById(CategoryID.from(UUID.randomUUID()));

        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(actual.get().getName(), entity.getName());
        assertThat(actual.get()).hasFieldOrPropertyWithValue("name", "Action");
        assertTrue(actual.get().getIsActive());
    }

    @Test
    void updateCategory() {
        Category expected = new Category(
                "Action",
                "Action Description",
                true
        );

        doReturn(CategoryPersistence.from(expected))
                .when(categoryRepository)
                .save(any(CategoryPersistence.class));

        Category actual = repository.create(expected);

        actual.update("Horror", actual.getDescription(), false);

        doReturn(CategoryPersistence.from(actual))
                .when(categoryRepository)
                .save(any(CategoryPersistence.class));

        repository.update(actual);

        assertNotNull(actual);
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Horror");
        assertFalse(actual.getIsActive());
    }

    @Test
    void deleteCategory() {
        Category entity = new Category(
                "Action",
                "Action Description",
                true
        );

        CategoryPersistence expected = CategoryPersistence.from(entity);
        doNothing()
                .when(categoryRepository)
                .deleteById(entity.getId().getValue());
        when(categoryRepository.existsById(any())).thenReturn(true);


        repository.deleteById(entity.getId());

        verify(categoryRepository, times(1)).deleteById(entity.getId().getValue());
    }

}
