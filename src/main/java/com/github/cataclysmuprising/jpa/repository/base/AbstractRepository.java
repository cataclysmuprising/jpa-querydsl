package com.github.cataclysmuprising.jpa.repository.base;

import com.github.cataclysmuprising.jpa.criteria.AbstractCriteria;
import com.github.cataclysmuprising.jpa.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T extends AbstractEntity, C extends AbstractCriteria, ID> extends BaseRepository<T, ID> {

	Optional<T> findById(ID id);

	<S extends T> S save(S entity);

	<S extends T> S saveAndFlush(S entity);

	<S extends T> List<S> saveAll(Iterable<S> entities);

	long update(T entity, C criteria);

	void deleteById(ID id);

	void delete(T entity);

	long delete(C criteria);

	void flush();
}
