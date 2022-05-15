package com.fullcycle.catalogovideo.application.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerTest extends BaseIT{

    private JacksonTester<CreateCategoryInputData> createJson;

    private JacksonTester<UpdateCategoryInputData> updateJson;

    @BeforeEach
    void init() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @Order(1)
    @Sql({"/create-category.sql"})
    void testFindCategoryById() {
        String id = "54f22ca3-866f-46d3-a149-198090353651";
        ResponseEntity<CategoryOutputData> response = restTemplate.getForEntity( "/categories/{id}",CategoryOutputData.class, id);
        CategoryOutputData category =  response.getBody();

        assertNotNull(category);
        assertEquals(UUID.fromString(id),category.getId());
        assertEquals("Ação", category.getName());
    }

    @Test
    @Order(2)
    void testFindAllCategory() {
        ResponseEntity<CategoryOutputData[]> response = restTemplate.getForEntity( "/categories", CategoryOutputData[].class);
        List<CategoryOutputData> categories =  List.of(Objects.requireNonNull(response.getBody()));

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(2, categories.size());
    }

    @Test
    @Order(3)
    void testCreateCategory() throws IOException {

        CreateCategoryInputData input = new CreateCategoryInputData();
        input.setName("Action");
        input.setDescription("Action description");

        String payload = createJson.write(input).getJson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<CategoryOutputData> response = restTemplate.postForEntity( "/categories", entity, CategoryOutputData.class);
        CategoryOutputData category =  response.getBody();

        assertNotNull(category);
        assertEquals("Action", category.getName());
        assertEquals("Action description", category.getDescription());
    }

    @Test
    @Order(4)
    void testUpdateCategory() throws IOException {

        Category update = new Category(
                UUID.fromString("54f22ca3-866f-46d3-a149-198090353651"),
                "Action",
                "Horror description",
                true
        );

        UpdateCategoryInputData input = new UpdateCategoryInputData();
        input.setName("Horror");
        input.setDescription(update.getDescription());
        input.setIsActive(update.getIsActive());

        String payload = updateJson.write(input).getJson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        restTemplate.put( "/categories/{id}", entity, update.getId());

        ResponseEntity<CategoryOutputData> response = restTemplate.getForEntity( "/categories/{id}",
                CategoryOutputData.class, update.getId());

        CategoryOutputData category =  response.getBody();

        assertNotNull(category);
        assertEquals("Horror", category.getName());
        assertTrue(category.isActive());
        assertEquals("Horror description", category.getDescription());
    }

    @Test
    @Order(5)
    void testRemoveCategory() {
        String id = "54f22ca3-866f-46d3-a149-198090353651";
        restTemplate.delete( "/categories/{id}", UUID.fromString(id));
        ResponseEntity<String> response = restTemplate.getForEntity( "/categories/{id}", String.class, id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Category 54f22ca3-866f-46d3-a149-198090353651 not found", response.getBody());
    }
}
