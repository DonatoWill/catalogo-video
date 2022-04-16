package com.fullcycle.catalogovideo.application.usecase.category.update;

import java.util.UUID;

public interface IUpdateCategoryUseCase{

    void execute(UUID id, UpdateCategoryInputData inputData);

}
