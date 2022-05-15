package com.fullcycle.catalogovideo.usecase;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN input);
}
