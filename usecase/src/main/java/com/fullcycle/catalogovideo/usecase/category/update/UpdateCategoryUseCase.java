package com.fullcycle.catalogovideo.usecase.category.update;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.entity.CategoryID;
import com.fullcycle.catalogovideo.domain.exceptions.NotFoundException;
import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.repository.ICategoryRepository;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateCategoryUseCase extends IUpdateCategoryUseCase {

    private ICategoryRepository repository;

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryInputData inputData) {

        final var notification = Notification.create();

        var category = repository.findById(CategoryID.from(inputData.getId()))
                .orElseThrow(() -> NotFoundException.with(Category.class, CategoryID.from(inputData.getId())));

        category.update(
                inputData.getName(),
                inputData.getDescription(),
                inputData.getIsActive()
        );
        category.validate(notification);

        return notification.hasError()? API.Left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(Category category) {
        return API.Try(() -> repository.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

}
