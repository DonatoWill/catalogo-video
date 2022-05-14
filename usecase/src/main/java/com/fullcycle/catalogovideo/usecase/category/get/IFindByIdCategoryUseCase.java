package com.fullcycle.catalogovideo.usecase.category.get;

import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;

import java.util.UUID;

public interface IFindByIdCategoryUseCase {

    CategoryOutputData execute(UUID id);

}
