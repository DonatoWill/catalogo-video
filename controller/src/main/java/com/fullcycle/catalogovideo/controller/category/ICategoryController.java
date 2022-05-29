package com.fullcycle.catalogovideo.controller.category;

import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Operations pertaining to categories in CatalogoVideo")
public interface ICategoryController {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category supplied"),
            @ApiResponse(responseCode = "409", description = "Category already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> createCategory(@RequestBody CreateCategoryInputData input);

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK) //indica qual tipo de resposta caso sucess ele irá emitir
    @Operation(summary = "List all categories paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Pagination<CategoryOutputData> findAll(CategorySearchQuery query);

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Find a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    CategoryOutputData findById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    void deleteById(@PathVariable("id") String id);

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Update a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    void update(@PathVariable String id, @RequestBody UpdateCategoryInputData input);

}

