package com.fullcycle.catalogovideo.infrastructure.category.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryPersistence, String> {
    Page<CategoryPersistence> findAll(Specification<CategoryPersistence> whereClause, Pageable page);
}
