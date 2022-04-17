package com.fullcycle.catalogovideo.infrastructure.mysql;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.data.SpringDataCategoryRepository;
import com.fullcycle.catalogovideo.infrastructure.persistence.CategoryPersistence;
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
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MySQLICategoryRepositoryImplTests {

    @InjectMocks
    private MySqlRepositoryICategoryImpl repository;

    @Mock
    private SpringDataCategoryRepository springDataRepository;

    @BeforeEach
    void setUp() {
        repository = new MySqlRepositoryICategoryImpl(springDataRepository);
    }

    @Test
    public void saveCategory() {
        Category expected = new Category(
                "Action",
                "Action Description",
                true
        );

        doReturn(CategoryPersistence.from(expected))
                .when(springDataRepository)
                .save(any(CategoryPersistence.class));

        Category actual = repository.create(expected);

        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Action");
        assertTrue(actual.getIsActive());

    }

    @Test
    void findAllCategories() {
        Category entity1 = new Category(
                "Action",
                "Action Description",
                true
        );

        Category entity2 = new Category(
                "Horror",
                "Horror Description",
                true
        );

        List<CategoryPersistence> expected = List.of(
                CategoryPersistence.from(entity1),
                CategoryPersistence.from(entity2)
        );
        doReturn(expected)
                .when(springDataRepository)
                .findAll();

        List<Category> actual = repository.findAll();

        assertNotNull(actual);
        assertThat(actual).isNotEmpty();
        assertThat(actual).hasSize(2);
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
                .when(springDataRepository)
                .findById(any(UUID.class));

        Optional<Category> actual = repository.findById(UUID.randomUUID());

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
                .when(springDataRepository)
                .save(any(CategoryPersistence.class));

        Category actual = repository.create(expected);

        actual.update("Horror", actual.getDescription(), false);

        doReturn(CategoryPersistence.from(actual))
                .when(springDataRepository)
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
                .when(springDataRepository)
                .deleteById(entity.getId());

        repository.remove(entity.getId());

        verify(springDataRepository, times(1)).deleteById(entity.getId());
    }
}
