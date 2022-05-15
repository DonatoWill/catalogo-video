package com.fullcycle.catalogovideo.infrastructure.mysql;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.infrastructure.data.SpringDataCategoryRepository;
import com.fullcycle.catalogovideo.infrastructure.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MySqlRepositoryICategoryImpl implements ICategoryRepository {

    private SpringDataCategoryRepository repository;
    @Override
    public Pagination<Category> findAll() {
        return null;//repository.findAll()
//                .parallelStream()
//                .map(CategoryPersistence::fromThis)
//                .collect(Pagination::map);
    }

    @Override
    public Category create(Category category) {
        final CategoryPersistence entity = CategoryPersistence.from(category);
        return repository.save(entity).fromThis();
    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        return repository.findById(id.getValue())
                .map(CategoryPersistence::fromThis);
    }

    @Override
    public void remove(CategoryID id) {
        repository.deleteById(id.getValue());
    }

    @Override
    public Category update(Category category) {
        final CategoryPersistence entity = CategoryPersistence.from(category);
        return repository.save(entity).fromThis();
    }
}
