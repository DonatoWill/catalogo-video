package com.fullcycle.catalogovideo.domain;

import com.fullcycle.catalogovideo.domain.validation.ValidationHandler;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(ID id) {
        super(id);
    }


}
