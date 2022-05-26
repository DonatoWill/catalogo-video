package com.fullcycle.catalogovideo.application.category.usecase.retrieve.findall;

import com.fullcycle.catalogovideo.application.IntegrationTest;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.category.findall.FindAllCategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
public class FindAllCategoriesUseCaseIT {

    @Autowired
    private FindAllCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void mockUp(){
       final var categories =  Stream.of(
                Category.newCategory("Filmes", null, true),
                Category.newCategory("Netflix Originals", "Títulos originais da Netflix", true),
                Category.newCategory("Amazon Originals", "Títulos originais da Amazon", true),
                Category.newCategory("Documentários", null, true),
                Category.newCategory("Sports", null, true),
                Category.newCategory("Kids", null, true),
                Category.newCategory("Series", null, true)
        ).map(CategoryPersistence::from)
               .collect(Collectors.toList());

        repository.saveAllAndFlush(categories);
    }



    @Test
    public void givenAValidTerm_whenTermDoesNotMatchsPrePersisted_shouldReturnEmptyPage(){

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "ssdadasda";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0;
        final var expectedTotal = 0;

        final var aQuery = new CategorySearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var actualResult = useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.getItems().size());
        assertEquals(expectedPage, actualResult.getCurrentPage());
        assertEquals(expectedTotal, actualResult.getTotal());

    }
}
