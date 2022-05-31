package com.fullcycle.catalogovideo.controller.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.catalogovideo.controller.ControllerTest;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.domain.exceptions.NotFoundException;
import com.fullcycle.catalogovideo.domain.validation.Error;
import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.create.AbstractCreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryOutput;
import com.fullcycle.catalogovideo.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.IUpdateCategoryUseCase;

import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = ICategoryController.class)
public class CategoryControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbstractCreateCategoryUseCase createUseCase;
    @MockBean
    private IFindByIdCategoryUseCase findByIdUseCase;
    @MockBean
    private IUpdateCategoryUseCase updateUseCase;
    @MockBean
    private IRemoveCategoryUseCase removeUseCase;
    @MockBean
    private IFindAllCategoryUseCase findAllUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = new CreateCategoryInputData(expectedName, expectedDescription, expectedIsActive);

        when(createUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from("123")));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(aCommand));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/categories/123"),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.id", equalTo("123"))
                );

        verify(createUseCase, times(1)).execute(argThat(cmd ->
                        Objects.equals(expectedName, cmd.getName())
                && Objects.equals(expectedDescription, cmd.getDescription())
                && Objects.equals(expectedIsActive, cmd.getIsActive())
                ));
    }

    @Test
    void givenAInvalidCommand_whenCallsCreateCategory_shouldReturnException() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = new CreateCategoryInputData(expectedName, expectedDescription, expectedIsActive);

        when(createUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error("Name should not be null"))));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(aCommand));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Location", nullValue()),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo("Name should not be null"))
                );

        verify(createUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.getName())
                        && Objects.equals(expectedDescription, cmd.getDescription())
                        && Objects.equals(expectedIsActive, cmd.getIsActive())
        ));
    }

    @Test
    void givenAInvalidCommand_whenCallsCreateCategory_shouldReturnDomainException() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = new CreateCategoryInputData(expectedName, expectedDescription, expectedIsActive);

        when(createUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error("Name should not be null")));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(aCommand));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Location", nullValue()),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo("Name should not be null"))
                );

        verify(createUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.getName())
                        && Objects.equals(expectedDescription, cmd.getDescription())
                        && Objects.equals(expectedIsActive, cmd.getIsActive())
        ));
    }

    @Test
    void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(
                expectedName, expectedDescription, expectedIsActive
        );

        final var expectedId = aCategory.getId().getValue();

        when(findByIdUseCase.execute(any()))
                .thenReturn(CategoryOutputData.fromDomain(aCategory));

        final var request = get("/categories/{id}", expectedId);

        final var response = this.mvc.perform(request).andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())));

        verify(findByIdUseCase, times(1)).execute(expectedId);

    }

    @Test
    void givenAInvalidId_whenCallsGetCategory_shouldThrowError() throws Exception {

        final var expectedError = "Category with Id 123 was not found";
        final var expectedId = CategoryID.from("123").getValue();

        when(findByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(
                        Category.class,
                        CategoryID.from(expectedId)
                        )
                );

        final var request = get("/categories/{id}", expectedId);
        final var response = this.mvc.perform(request).andDo(print());
        response.andExpect(status().isNotFound());
        verify(findByIdUseCase, times(1)).execute(expectedId);

    }

    @Test
    void givenAValidId_whenCallsUpdateCategory_shouldReturnCategoryId() throws Exception {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = "123";

        when(updateUseCase.execute(any()))
                .thenReturn(Right(UpdateCategoryOutput.from(expectedId)));

        final var input = new UpdateCategoryInputData(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        final var response = this.mvc.perform(request).andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(updateUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.getName())
                && Objects.equals(expectedDescription, cmd.getDescription())
                && Objects.equals(expectedIsActive, cmd.getIsActive())
                ));

    }

}
