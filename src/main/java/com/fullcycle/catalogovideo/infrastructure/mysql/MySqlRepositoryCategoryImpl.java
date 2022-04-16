package com.fullcycle.catalogovideo.infrastructure.mysql;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.infrastructure.data.SpringDataCategoryRepository;
import com.fullcycle.catalogovideo.infrastructure.persistence.CategoryPersistence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MySqlRepositoryCategoryImpl implements ICategoryRepository {

    private SpringDataCategoryRepository repository;
    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Category create(Category category) {
        final CategoryPersistence entity = CategoryPersistence.from(category);
        return repository.save(entity).fromThis();
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public void update(Category category) {

    }
}
