package com.fullcycle.catalogovideo.application.usecase.category.get;

import com.fullcycle.catalogovideo.application.usecase.category.common.CategoryOutputData;

import java.util.UUID;

public interface IFindByIdCategoryUseCase {

    CategoryOutputData execute(UUID id);

}
