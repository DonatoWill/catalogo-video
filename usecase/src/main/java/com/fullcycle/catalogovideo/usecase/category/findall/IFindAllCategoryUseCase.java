package com.fullcycle.catalogovideo.usecase.category.findall;

import com.fullcycle.catalogovideo.usecase.UseCase;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;

public abstract class IFindAllCategoryUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryOutputData>> {

}
