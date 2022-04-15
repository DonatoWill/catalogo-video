package com.fullcycle.catalogovideo.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fullcycle.catalogovideo.domain.exceptions.DomainException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
    public void throwDoaminExceptionWhenNameIsNull(){        
        assertThrows(DomainException.class, () -> new Category(null, "Description 2"));
    }

    @Test
    public void throwDoaminExceptionWhenNameIsBlank(){
        assertThrows(DomainException.class, () -> new Category(" ", "Description 2"));
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
}
