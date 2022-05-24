package com.fullcycle.catalogovideo.infrastructure.category;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryPersistence;
import com.fullcycle.catalogovideo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.catalogovideo.infrastructure.utils.SpecificationUtils;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.fullcycle.catalogovideo.infrastructure.utils.SpecificationUtils.like;

@Component
public class CategoryMySQLRepository implements ICategoryRepository {

    private final CategoryRepository repository;

    public CategoryMySQLRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery query) {

        //Paginação
        final var page = PageRequest.of(
                query.getPage(),
                query.getPerPage(),
                Sort.by(Sort.Direction.fromString(query.getDirection()), query.getSort())
        );

        // Busca dinâmica
        final var specifications = Optional.ofNullable(query.getTerms())
                .filter(str -> !str.isBlank())
                .map(str -> SpecificationUtils
                        .<CategoryPersistence>like("name", str)
                        .or(like("description", str))
                ).orElse(null);

        final var pageResult = repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryPersistence::toAggregate).toList()
        );
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
    public void deleteById(CategoryID id) {
        if(repository.existsById(id.getValue())){
            repository.deleteById(id.getValue());
        }
    }

    @Override
    public Category update(Category category) {
        return repository.save(CategoryPersistence.from(category)).toAggregate();
    }
}
