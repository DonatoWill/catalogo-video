package com.fullcycle.catalogovideo.usecase.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T>{

    int currentPage;
    int perPage;
    long total;
    List<T> items;

    public <R> Pagination<R> map(final Function<T, R> mapper){
        final List<R> newList = this.items.stream()
                .map(mapper)
                .toList();
        return new Pagination<>(this.currentPage, this.perPage, this.total, newList);
    }
}
