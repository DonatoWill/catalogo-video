package com.fullcycle.catalogovideo.usecase.repository;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;

import java.util.Optional;

public interface ICategoryRepository {
    Pagination<Category> findAll(CategorySearchQuery query);
    Category create(Category category);
    Optional<Category> findById(CategoryID id);
    void deleteById(CategoryID id);
    Category update(Category category);
}
