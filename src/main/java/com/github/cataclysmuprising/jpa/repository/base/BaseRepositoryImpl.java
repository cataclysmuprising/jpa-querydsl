package com.github.cataclysmuprising.jpa.repository.base;

import com.github.cataclysmuprising.jpa.criteria.AbstractCriteria;
import com.github.cataclysmuprising.jpa.entity.AbstractEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import java.io.Serializable;

public abstract class BaseRepositoryImpl<T extends AbstractEntity, C extends AbstractCriteria, ID extends Serializable> extends QuerydslJpaPredicateExecutor<T> implements AbstractRepository<T, C, ID> {

	private static final EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager, resolver, null);
	}
}