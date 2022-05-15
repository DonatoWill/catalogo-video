package com.fullcycle.catalogovideo.usecase.category.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategorySearchQuery {
    int page;
    int perPage;
    String terms;
    String sort;
    String direction;
}
