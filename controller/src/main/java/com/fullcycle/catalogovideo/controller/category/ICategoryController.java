package com.fullcycle.catalogovideo.controller.category;

import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryInputData;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryInputData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/categories")
@Api(value = "Categories", description = "Operations pertaining to categories in CatalogoVideo")
public interface ICategoryController {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category created successfully"),
            @ApiResponse(code = 400, message = "Invalid category supplied"),
            @ApiResponse(code = 409, message = "Category already exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    CategoryOutputData createCategory(@RequestBody CreateCategoryInputData input);

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK) //indica qual tipo de resposta caso sucess ele irá emitir
    @ApiOperation(value = "List all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    Pagination<CategoryOutputData> findAll(CategorySearchQuery query);

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Find a category by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved category"),
            @ApiResponse(code = 404, message = "Category not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    CategoryOutputData findById(@PathVariable("id") UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Category deleted successfully"),
            @ApiResponse(code = 404, message = "Category not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    void deleteById(@PathVariable("id") UUID id);

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a category")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Category updated successfully"),
            @ApiResponse(code = 404, message = "Category not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    void update(@PathVariable String id, @RequestBody UpdateCategoryInputData input);

}

