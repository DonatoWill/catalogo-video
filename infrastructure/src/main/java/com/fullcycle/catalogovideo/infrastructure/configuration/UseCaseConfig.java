package com.fullcycle.catalogovideo.infrastructure.configuration;

import com.fullcycle.catalogovideo.infrastructure.category.CategoryMySQLRepository;
import com.fullcycle.catalogovideo.usecase.category.create.AbstractCreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.create.CreateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.delete.IRemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.delete.RemoveCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.FindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.findall.IFindAllCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.FindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.get.IFindByIdCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.IUpdateCategoryUseCase;
import com.fullcycle.catalogovideo.usecase.category.update.UpdateCategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final CategoryMySQLRepository categoryRepository;

    public UseCaseConfig(CategoryMySQLRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Bean
    public AbstractCreateCategoryUseCase createCategoryUseCase(){
        return new CreateCategoryUseCase(categoryRepository);
    }

    @Bean
    public IUpdateCategoryUseCase updateCategoryUseCase(){
        return new UpdateCategoryUseCase(categoryRepository);
    }

    @Bean
    public IFindAllCategoryUseCase findAllCategoryUseCase(){
        return new FindAllCategoryUseCase(categoryRepository);
    }

    @Bean
    public IFindByIdCategoryUseCase findByIdCategoryUseCase(){
        return new FindByIdCategoryUseCase(categoryRepository);
    }

    @Bean
    public IRemoveCategoryUseCase removeCategoryUseCase(){
        return new RemoveCategoryUseCase(categoryRepository);
    }

}
