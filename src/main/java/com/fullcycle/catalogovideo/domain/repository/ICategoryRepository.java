package com.fullcycle.catalogovideo.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fullcycle.catalogovideo.domain.entity.Category;

public interface ICategoryRepository {
    List<Category> findAll();
    Category create(Category category);
    Optional<Category> findById(UUID id);
    void remove(UUID id);
    void update(Category category);
}
