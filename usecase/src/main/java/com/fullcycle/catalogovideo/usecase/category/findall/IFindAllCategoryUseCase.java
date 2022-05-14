package com.fullcycle.catalogovideo.usecase.category.findall;

import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;

import java.util.List;

public interface IFindAllCategoryUseCase {

    List<CategoryOutputData> execute();

}
