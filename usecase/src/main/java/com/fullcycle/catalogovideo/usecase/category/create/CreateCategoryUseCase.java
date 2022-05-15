package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateCategoryUseCase extends AbstractCreateCategoryUseCase {

    private ICategoryRepository repository;

    @Override
    public Either<Notification, CategoryOutputData> execute(CreateCategoryInputData inputData) {
        final var notification = Notification.create();
        final var category = toDomain(inputData);
        category.validate(notification);

        return notification.hasError() ? Either.left(notification) : create(category);
    }

    private Either<Notification, CategoryOutputData> create(final Category category) {
        return API.Try(() -> repository.create(category))
                .toEither()
                .bimap(Notification::create, CategoryOutputData::fromDomain);
    }

    private Category toDomain(CreateCategoryInputData inputData) {
        return new Category(inputData.getName(), inputData.getDescription(), inputData.getIsActive());
    }


}
