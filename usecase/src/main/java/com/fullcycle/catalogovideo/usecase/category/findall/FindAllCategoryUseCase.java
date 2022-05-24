package com.fullcycle.catalogovideo.usecase.category.findall;

import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.usecase.category.common.CategorySearchQuery;
import com.fullcycle.catalogovideo.usecase.pagination.Pagination;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllCategoryUseCase extends IFindAllCategoryUseCase{

    private ICategoryRepository categoryRepository;

    @Override
    public Pagination<CategoryOutputData> execute(CategorySearchQuery query) {
        var categories = categoryRepository.findAll(query);

        return categories.map(CategoryOutputData::fromDomain);
    }
}
