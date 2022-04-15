package com.fullcycle.catalogovideo.application.usecase.category.findall;

import com.fullcycle.catalogovideo.application.usecase.category.common.CategoryOutputData;
import com.fullcycle.catalogovideo.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FindAllCategoryUseCase implements IFindAllCategoryUseCase{

    private ICategoryRepository categoryRepository;

    @Override
    public List<CategoryOutputData> execute() {
        var list = categoryRepository.findAll();

        return list.stream()
                .map(CategoryOutputData::fromDomain)
                .collect(Collectors.toList());
    }
}
