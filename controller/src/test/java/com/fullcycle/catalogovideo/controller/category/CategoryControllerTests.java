package com.fullcycle.catalogovideo.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.catalogovideo.controller.configuration.GlobalExceptionHandler;
import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.create.AbstractCreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.IUpdateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    private AbstractCreateCategoryUseCase createUseCase;

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
                entity.getId().getValue(),
                entity.getName(),
                entity.getDescription(),
                entity.isIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );

        when(createUseCase.execute(any())).thenReturn(Either.right(output));

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
                entity.getId().getValue(),
                entity.getName(),
                entity.getDescription(),
                entity.isIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );

        CategoryOutputData output2 = new CategoryOutputData(
                entity2.getId().getValue(),
                entity2.getName(),
                entity2.getDescription(),
                entity2.isIsActive(),
                entity2.getCreatedAt(),
                entity2.getUpdatedAt(),
                entity2.getDeletedAt()
        );

        List<CategoryOutputData> output = List.of(output1, output2);

        var search = CategorySearchQuery.builder()
                        .direction("asc")
                        .page(1)
                        .build();

        when(findAllUseCase.execute(any())).thenReturn(new Pagination(1, 10, 20, output));

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("perPage", "10")
                .param("sort", "name")
                .param("terms", "")
                .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findCategoryById() throws Exception {
        Category entity = new Category(
                "Action",
                "",
                true
        );
        CategoryOutputData output = new CategoryOutputData(
                entity.getId().getValue(),
                entity.getName(),
                entity.getDescription(),
                entity.isIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );

        doReturn(output).when(findByIdUseCase).execute(any());

        mockMvc.perform(get("/categories/{id}", UUID.fromString(entity.getId().getValue()))
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

        doNothing().when(removeUseCase).execute(any());
        mockMvc.perform(delete("/categories/{id}", UUID.fromString(entity.getId().getValue()))
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

        CategoryOutputData output = new CategoryOutputData(
                entity.getId().getValue(),
                entity.getName(),
                entity.getDescription(),
                entity.isIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );

        UpdateCategoryInputData input = new UpdateCategoryInputData();
        input.setId(entity.getId().getValue());
        input.setName("Horror");
        input.setDescription(entity.getDescription());
        input.setIsActive(entity.getIsActive());

        String payload = updateJson.write(input).getJson();

        when(updateUseCase.execute(any(UpdateCategoryInputData.class))).thenReturn(Either.right(output));

        mockMvc.perform(put("/categories/{id}", entity.getId().getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isNoContent());
    }
}
