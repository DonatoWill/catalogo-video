package com.fullcycle.catalogovideo.usecase.category.create;

import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.UseCase;
import io.vavr.control.Either;

public abstract class AbstractCreateCategoryUseCase
        extends UseCase<CreateCategoryInputData, Either<Notification, CreateCategoryOutput>> {
}
