package com.fullcycle.catalogovideo.usecase.category.update;

import com.fullcycle.catalogovideo.domain.validation.handler.Notification;
import com.fullcycle.catalogovideo.usecase.UseCase;
import com.fullcycle.catalogovideo.usecase.category.common.CategoryOutputData;
import io.vavr.control.Either;

public abstract class IUpdateCategoryUseCase extends UseCase<UpdateCategoryInputData, Either<Notification, UpdateCategoryOutput>> {



}
