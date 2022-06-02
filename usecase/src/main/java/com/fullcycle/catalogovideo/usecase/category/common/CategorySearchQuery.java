package com.fullcycle.catalogovideo.usecase.category.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategorySearchQuery {
    int page;
    int perPage;
    String search;
    String sort;
    String dir;
}
