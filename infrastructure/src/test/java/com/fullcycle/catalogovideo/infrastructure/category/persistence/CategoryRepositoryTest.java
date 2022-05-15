package com.fullcycle.catalogovideo.infrastructure.category.persistence;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.infrastructure.MySQLRepositoryTest;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@MySQLRepositoryTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CategoryRepositoryTest {


    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void givenAnInvalidNullName_whenCallSave_sholdReturnError(){
        final var aCategory = Category.newCategory("Filme", "A categoria mais assistida", true);
        final var exceptionCause = "NULL not allowed for column \"NAME\"; SQL statement:\n" +
                "insert into category (active, created_at, deleted_at, description, name, updated_at, id) values (?, ?, ?, ?, ?, ?, ?) [23502-200]";

        var entity = CategoryPersistence.from(aCategory);
        entity.setName(null);

        final var actualException =
                assertThrows(DataIntegrityViolationException.class, () -> {
                    categoryRepository.save(entity);
                });


        final var actualCause =
                assertInstanceOf(ConstraintViolationException.class, actualException.getCause());

        assertEquals(exceptionCause, actualCause.getCause().getMessage());
    }

    @Test
    void givenAnInvalidNullCreated_whenCallSave_sholdReturnError(){
        final var aCategory = Category.newCategory("Filme", "A categoria mais assistida", true);
        final var exceptionCause = "NULL not allowed for column \"CREATED_AT\"; SQL statement:\n" +
                "insert into category (active, created_at, deleted_at, description, name, updated_at, id) values (?, ?, ?, ?, ?, ?, ?) [23502-200]";

        var entity = CategoryPersistence.from(aCategory);
        entity.setCreatedAt(null);

        final var actualException =
                assertThrows(DataIntegrityViolationException.class, () -> {
                    categoryRepository.save(entity);
                });


        final var actualCause =
                assertInstanceOf(ConstraintViolationException.class, actualException.getCause());

        assertEquals(exceptionCause, actualCause.getCause().getMessage());
    }
}
