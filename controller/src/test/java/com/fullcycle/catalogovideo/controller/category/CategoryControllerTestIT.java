package com.fullcycle.catalogovideo.controller.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.catalogovideo.controller.ControllerTest;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.domain.validation.Error;
import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.category.create.AbstractCreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryOutput;
import com.fullcycle.catalogovideo.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.IUpdateCategoryUseCase;
import io.vavr.control.Either;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

        final var request = MockMvcRequestBuilders.post("/categories")
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

        final var request = MockMvcRequestBuilders.post("/categories")
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

        final var request = MockMvcRequestBuilders.post("/categories")
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
}
