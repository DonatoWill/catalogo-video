package com.fullcycle.catalogovideo.infrastructure.category;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMySQLRepository implements ICategoryRepository {

    private final CategoryRepository repository;

    public CategoryMySQLRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagination<Category> findAll() {
        return null;//repository.findAll();
    }

    @Override
    public Category create(Category category) {
        return repository.save(CategoryPersistence.from(category)).toAggregate();
    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        return repository.findById(id.getValue()).map(CategoryPersistence::toAggregate);
    }

    @Override
    public void remove(CategoryID id) {
        repository.deleteById(id.getValue());
    }

    @Override
    public Category update(Category category) {
        return repository.save(CategoryPersistence.from(category)).toAggregate();
    }
}
