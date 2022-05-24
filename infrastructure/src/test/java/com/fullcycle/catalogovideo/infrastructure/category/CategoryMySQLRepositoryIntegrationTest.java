package com.fullcycle.catalogovideo.infrastructure.category;


import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.infrastructure.MySQLRepositoryTest;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MySQLRepositoryTest
class CategoryMySQLRepositoryIntegrationTest {

    @Autowired
    private CategoryMySQLRepository categoryMySQLRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryMySQLRepository.create(aCategory);

        assertEquals(1, categoryRepository.count());

        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.getIsActive());
        assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(actualCategory.getId().getValue()).get();

        assertNotNull(actualEntity);
        assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        assertEquals(expectedName, actualEntity.getName());
        assertEquals(expectedDescription, actualEntity.getDescription());
        assertEquals(expectedIsActive, actualEntity.isActive());
        assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        assertEquals(aCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());

    }

    @Test
    void givenAValidCategory_whenCallsUpdate_shouldReturnUpdatedCategory() throws InterruptedException {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryPersistence.from(aCategory));

        final var expectedNameUpdated = "Séries";
        final var expectedDescriptionUpdated = "A categoria mais assistida";
        final var expectedIsActiveUpdated = true;

        final var updatedCategory = aCategory.clone().update(expectedNameUpdated, expectedDescriptionUpdated, expectedIsActiveUpdated);

        final var actualUpdatedCategory = categoryMySQLRepository.update(updatedCategory);

        assertEquals(1, categoryRepository.count());

        assertEquals(expectedNameUpdated, actualUpdatedCategory.getName());
        assertEquals(expectedDescriptionUpdated, actualUpdatedCategory.getDescription());
        assertEquals(expectedIsActiveUpdated, actualUpdatedCategory.getIsActive());
        assertEquals(aCategory.getCreatedAt(), actualUpdatedCategory.getCreatedAt());
        assertTrue(aCategory.getUpdatedAt().isBefore(actualUpdatedCategory.getUpdatedAt()));
        assertNull(actualUpdatedCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(actualUpdatedCategory.getId().getValue()).get();

        assertNotNull(actualEntity);
        assertEquals(updatedCategory.getId().getValue(), actualEntity.getId());
        assertEquals(expectedNameUpdated, actualEntity.getName());
        assertEquals(expectedDescriptionUpdated, actualEntity.getDescription());
        assertEquals(expectedIsActiveUpdated, actualEntity.isActive());
        assertEquals(updatedCategory.getCreatedAt(), actualEntity.getCreatedAt());
        assertEquals(updatedCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        assertEquals(updatedCategory.getDeletedAt(), actualEntity.getDeletedAt());

    }

    @Test
    void givenAPrePersistedCategoryAndValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory(){

        final var aCategory = Category.newCategory("Filmes", null, true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryPersistence.from(aCategory));

        assertEquals(1, categoryRepository.count());

        categoryMySQLRepository.deleteById(aCategory.getId());

        assertEquals(0, categoryRepository.count());

    }

    @Test
    void givenInvalidCategoryId_whenTryToDeleteIt_shouldNotDelete(){

        final var aCategory = Category.newCategory("Filmes", null, true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryPersistence.from(aCategory));

        assertEquals(1, categoryRepository.count());

        categoryMySQLRepository.deleteById(CategoryID.from("invalid"));

        assertEquals(1, categoryRepository.count());

    }

    @Test
    void givenAValidCategory_whenCallsFindById_shouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryRepository.saveAndFlush(CategoryPersistence.from(aCategory));

        assertEquals(1, categoryRepository.count());

        final var actualEntity = categoryMySQLRepository.findById(aCategory.getId()).get();

        assertNotNull(actualEntity);
        assertEquals(actualCategory.getId(), actualEntity.getId().getValue());
        assertEquals(actualCategory.getName(), actualEntity.getName());
        assertEquals(actualCategory.getDescription(), actualEntity.getDescription());
        assertEquals(actualCategory.isActive(), actualEntity.getIsActive());
        assertEquals(actualCategory.getCreatedAt(), actualEntity.getCreatedAt());
        assertEquals(actualCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        assertEquals(actualCategory.getDeletedAt(), actualEntity.getDeletedAt());

    }

    @Test
    void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryPersistence.from(filmes),
                CategoryPersistence.from(series),
                CategoryPersistence.from(documentarios)
        ));

        assertEquals(3, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryMySQLRepository.findAll(query);

        assertEquals(expectedPage, actualResult.getCurrentPage());
        assertEquals(expectedPerPage, actualResult.getPerPage());
        assertEquals(expectedTotal, actualResult.getTotal());
        assertEquals(expectedPerPage, actualResult.getItems().size());
        assertEquals(documentarios.getId(), actualResult.getItems().get(0).getId());
    }

    @Test
    void givenEmptyCategories_whenCallsFindAll_shouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        assertEquals(0, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryMySQLRepository.findAll(query);

        assertEquals(expectedPage, actualResult.getCurrentPage());
        assertEquals(expectedPerPage, actualResult.getPerPage());
        assertEquals(expectedTotal, actualResult.getTotal());
        assertEquals(0, actualResult.getItems().size());
    }
}
