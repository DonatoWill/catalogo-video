package com.fullcycle.catalogovideo.domain.entity;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTests {

    @Test
    void createCategoryWithNameAndDescriptionNull() {
        final Category entity = new Category("Name 1", null);

        System.out.println(entity.getName());
        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertNull(entity.getDescription());
    }

    @Test
    void createCategoryWithNameAndDescription() {
        final Category entity = new Category("Name 1", "Description 2");

        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals("Description 2", entity.getDescription());
    }


    @Test
    void createCategoryAndActive() {
        final Category entity = new Category("Name 1", "Description 2");
        entity.activate();

        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals("Description 2", entity.getDescription());
        assertTrue(entity.isIsActive());
    }

    @Test
    void createCategoryAndIdDeactive() {
        final Category entity = new Category("Name 1", "Description 2");
        entity.deactivate();

        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals("Description 2", entity.getDescription());
        assertFalse(entity.getIsActive());
    }


    @Test
    void createCategoryAndUpdate() {
        final Category entity = new Category("Name 1", "Description 2");

        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals("Description 2", entity.getDescription());

        entity.update("Name 3", "Description 3", false);

        assertNotNull(entity);
        assert entity.getName().equals("Name 3");
        assertEquals("Description 3", entity.getDescription());
        assertFalse(entity.getIsActive());
    }

    @Test
    void givenAndInvalidNullName_whenCallNewCategoryAndValidate_thenShouldThrowDomainException() {
        final var actualException = assertThrows(DomainException.class, () ->
                new Category(null, "Description 2").validate(new ThrowsValidationHandler()));
        assertEquals("Name should not be null", actualException.getErrors().get(0).getMessage());
        assertEquals(1, actualException.getErrors().size());
    }

    @Test
    void givenAndInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldThrowDomainException() {
        final var actualException = assertThrows(DomainException.class, () ->
                new Category("", "Description 2").validate(new ThrowsValidationHandler()));
        assertEquals("Name should not be empty", actualException.getErrors().get(0).getMessage());
        assertEquals(1, actualException.getErrors().size());
    }

    @Test
    void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldThrowDomainException() {
        final var actualException = assertThrows(DomainException.class, () ->
                new Category("12 ", "Description 2").validate(new ThrowsValidationHandler()));
        assertEquals("Name should have between 3 and 255 characters", actualException.getErrors().get(0).getMessage());
        assertEquals(1, actualException.getErrors().size());
    }

    @Test
    void givenAnInvalidNameLengthGreaterThan255_whenCallNewCategoryAndValidate_thenShouldThrowDomainException() {

        final var name = "Desde ontem a noite o gerenciador de dependências do frontend superou o desempenho " +
                "na interpolação dinâmica de stringsDesde ontem a noite o gerenciador de dependências do frontend " +
                "superou o desempenho na interpolação dinâmica de stringsDesde ontem a noite o gerenciador de dependências " +
                "do frontend superou o desempenho na interpolação dinâmica de strings";

        final var actualException = assertThrows(DomainException.class, () ->
                new Category(name, "Description 2").validate(new ThrowsValidationHandler()));

        assertEquals("Name should have between 3 and 255 characters", actualException.getErrors().get(0).getMessage());
        assertEquals(1, actualException.getErrors().size());
    }

    @Test
    void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldNotThrowDomainException() {
        final var actualCategory = new Category("Name 1", "", true);
        assertDoesNotThrow(() ->
                actualCategory.validate(new ThrowsValidationHandler())
               );

        assertNotNull(actualCategory);
        assertEquals("Name 1", actualCategory.getName());
        assertEquals("", actualCategory.getDescription()) ;
        assertTrue(actualCategory.getIsActive());
    }

    @Test
    void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated(){
        final var aCategory = new Category("Name 1", "", true);
        assertDoesNotThrow(() ->
                aCategory.validate(new ThrowsValidationHandler())
        );

        final var actualCategory = aCategory.deactivate();

        assertDoesNotThrow(() ->
                aCategory.validate(new ThrowsValidationHandler())
        );

        assertNotNull(actualCategory);
        assertEquals("Name 1", actualCategory.getName());
        assertEquals("", actualCategory.getDescription()) ;
        assertFalse(actualCategory.getIsActive());
    }

    @Test
    void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){

        final var aCategory = new Category("Name 1", "", true);
        assertDoesNotThrow(() ->
                aCategory.validate(new ThrowsValidationHandler())
        );

        final var actualCategory = aCategory.update("Name 2", "Description 2", false);

        assertDoesNotThrow(() ->
                aCategory.validate(new ThrowsValidationHandler())
        );

        assertNotNull(actualCategory);
        assertEquals("Name 2", actualCategory.getName());
        assertEquals("Description 2", actualCategory.getDescription()) ;
        assertFalse(actualCategory.getIsActive());
    }


    @Test
    void givenAValidCategory_whenCallUpdatewithInvalidParam_thenReturnCategoryUpdated(){

        final var aCategory = new Category("Name 1", "", true);
        assertDoesNotThrow(() ->
                aCategory.validate(new ThrowsValidationHandler())
        );

        final var actualCategory =
                aCategory.update(null, "Description 2", false);

        assertNull(actualCategory.getName());
        assertEquals("Description 2", actualCategory.getDescription()) ;
        assertFalse(actualCategory.getIsActive());
    }

}
