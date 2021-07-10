package com.github.cataclysmuprising.jpa.repository.base;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BaseRepository<T, ID> extends QuerydslPredicateExecutor<T> {

}
