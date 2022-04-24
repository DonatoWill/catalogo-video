package com.fullcycle.catalogovideo.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.catalogovideo.api.configuration.GlobalExceptionHandler;
import com.fullcycle.catalogovideo.application.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.application.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.application.usecase.category.create.ICreateCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.update.IUpdateCategoryUseCase;
import com.fullcycle.catalogovideo.application.usecase.category.update.UpdateCategoryInputData;
import com.fullcycle.catalogovideo.domain.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTests {

    private MockMvc mockMvc;
    private JacksonTester<CreateCategoryInputData> createJson;

    private JacksonTester<UpdateCategoryInputData> updateJson;
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private ICreateCategoryUseCase createUseCase;

    @Mock
    private IFindAllCategoryUseCase findAllUseCase;

    @Mock
    private IFindByIdCategoryUseCase findByIdUseCase;

    @Mock
    private IRemoveCategoryUseCase removeUseCase;

    @Mock
    private IUpdateCategoryUseCase updateUseCase;

    @BeforeEach
    void init() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createCategory() throws Exception {
        CreateCategoryInputData input = new CreateCategoryInputData();
        input.setName("Action");

        String payload = createJson.write(input).getJson();

        Category entity = new Category(
                "Action",
                "",
                true
        );
        CategoryOutputData output = new CategoryOutputData(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive()
        );

        doReturn(output).when(createUseCase).execute(any(CreateCategoryInputData.class));

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Action")));
    }

    @Test
    void findAllCategories() throws Exception {
        Category entity = new Category(
                "Action",
                "",
                true
        );
        Category entity2 = new Category(
                "Horror",
                "",
                true
        );
        CategoryOutputData output1 = new CategoryOutputData(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive()
        );

        CategoryOutputData output2 = new CategoryOutputData(
                entity2.getId(),
                entity2.getName(),
                entity2.getDescription(),
                entity2.getIsActive()
        );

        List<CategoryOutputData> output = List.of(output1, output2);

        doReturn(output).when(findAllUseCase).execute();

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findCategoryById() throws Exception {
        Category entity = new Category(
                "Action",
                "",
                true
        );
        CategoryOutputData output = new CategoryOutputData(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive()
        );

        doReturn(output).when(findByIdUseCase).execute(any(UUID.class));

        mockMvc.perform(get("/categories/{id}", entity.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Action")));
    }

    @Test
    void removeCategoryById() throws Exception {
        Category entity = new Category(
                "Action",
                "",
                true
        );
        doNothing().when(removeUseCase).execute(any(UUID.class));
        mockMvc.perform(delete("/categories/{id}", entity.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCategoryById() throws Exception {
        Category entity = new Category(
                "Action",
                "",
                true
        );

        UpdateCategoryInputData input = new UpdateCategoryInputData();
        input.setName("Horror");
        input.setDescription(entity.getDescription());
        input.setIsActive(entity.getIsActive());

        String payload = updateJson.write(input).getJson();

        doNothing().when(updateUseCase).execute(any(UUID.class), any(UpdateCategoryInputData.class));

        mockMvc.perform(put("/categories/{id}", entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isNoContent());
    }
}