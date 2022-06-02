package com.fullcycle.catalogovideo.application.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.catalogovideo.application.BaseIT;
import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryOutput;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import net.minidev.json.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
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
class CategoryControllerTest extends BaseIT {

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
        assertEquals(id,category.getId());
        assertEquals("Ação", category.getName());
    }

    @Test
    @Order(2)
    void testFindAllCategory() {
        Pagination response =
                restTemplate.getForObject( "/categories?page=0&perPage=10&dir=asc&sort=name&search=", Pagination.class);

        List<CategoryOutputData> categories =  Objects.requireNonNull(response.getItems());

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
        input.setIsActive(true);

        String payload = createJson.write(input).getJson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        var response = restTemplate.postForEntity( "/categories", entity, CreateCategoryOutput.class);
        CreateCategoryOutput category =  response.getBody();

        assertNotNull(category);
        assertNotNull(category.getId());
    }

    @Test
    @Order(4)
    void testRemoveCategory() throws JSONException {
        String id = "54f22ca3-866f-46d3-a149-198090353651";
        restTemplate.delete( "/categories/{id}", UUID.fromString(id));
        ResponseEntity<String> response = restTemplate.getForEntity( "/categories/{id}", String.class, id);

        var jsonResponse = new JSONObject(response.getBody());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Category with Id "+ id +" was not found", jsonResponse.getString("message"));
    }
}
