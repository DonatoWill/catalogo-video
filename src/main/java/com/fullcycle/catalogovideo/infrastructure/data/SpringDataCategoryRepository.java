package com.fullcycle.catalogovideo.infrastructure.data;

import com.fullcycle.catalogovideo.infrastructure.persistence.CategoryPersistence;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringDataCategoryRepository extends CrudRepository<CategoryPersistence, UUID> {


}
