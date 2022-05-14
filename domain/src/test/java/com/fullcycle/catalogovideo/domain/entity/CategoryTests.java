package com.fullcycle.catalogovideo.domain.entity;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTests {

    @Test
    public void createCategoryWithNameAndDescriptionNull(){
        final Category entity = new Category("Name 1", null);
        
        System.out.println(entity.getName());
        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertNull(entity.getDescription());
    }

    @Test
    public void createCategoryWithNameAndDescription(){
        final Category entity = new Category("Name 1", "Description 2");
        
        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals(entity.getDescription(), "Description 2");
    }


    @Test
    public void createCategoryAndActive(){
        final Category entity = new Category("Name 1", "Description 2");
        entity.active();

        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals(entity.getDescription(), "Description 2");
        assertTrue(entity.active());
    }

    @Test
    public void createCategoryAndIdDeactive(){
        final Category entity = new Category("Name 1", "Description 2");
        entity.deactivate();

        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals(entity.getDescription(), "Description 2");
        assertFalse(entity.getIsActive());
    }


    @Test
    public void createCategoryAndUpdate(){
        final Category entity = new Category("Name 1", "Description 2");
        
        assertNotNull(entity);
        assert entity.getName().equals("Name 1");
        assertEquals(entity.getDescription(), "Description 2");

        entity.update("Name 3", "Description 3", false);

        assertNotNull(entity);
        assert entity.getName().equals("Name 3");
        assertEquals(entity.getDescription(), "Description 3");
        assertFalse(entity.getIsActive());
    }

    @Test
    public void givenAndInvalidNullName_whenCallNewCategoryAndValidate_thenShouldThrowDomainException(){

        final var actualException =assertThrows(DomainException.class, () -> new Category(null, "Description 2").validate(new ThrowsValidationHandler()));

        assertEquals("Name should not be null", actualException.getErrors().get(0).getMessage());
        assertEquals(1, actualException.getErrors().size());
    }
}
