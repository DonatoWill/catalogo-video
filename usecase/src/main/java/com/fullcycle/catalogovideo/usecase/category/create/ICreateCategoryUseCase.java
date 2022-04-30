package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;

public interface ICreateCategoryUseCase {

    CategoryOutputData execute(CreateCategoryInputData inputData);
}
