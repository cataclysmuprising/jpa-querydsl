package com.github.cataclysmuprising.jpa.repository.base;

import com.github.cataclysmuprising.jpa.criteria.AbstractCriteria;
import com.github.cataclysmuprising.jpa.entity.AbstractEntity;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepositoryImpl<T extends AbstractEntity, C extends AbstractCriteria, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AbstractRepository<T, C, ID> {
	protected final EntityManager entityManager;
	private final EntityPath<T> path;
	private final PathBuilder<T> builder;
	private final Querydsl querydsl;

	public AbstractRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		this(entityInformation, entityManager, SimpleEntityPathResolver.INSTANCE);
	}

	public AbstractRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {

		super(entityInformation, entityManager);

		path = resolver.createPath(entityInformation.getJavaType());
		builder = new PathBuilder<T>(path.getType(), path.getMetadata());
		querydsl = new Querydsl(entityManager, builder);
		this.entityManager = entityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QuerydslPredicateExecutor#findOne(com.mysema.query.types.Predicate)
	 */
	@Override
	public Optional<T> findOne(Predicate predicate) {

		try {
			return Optional.ofNullable(createQuery(predicate).select(path).fetchOne());
		}
		catch (NonUniqueResultException ex) {
			throw new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QuerydslPredicateExecutor#findAll(com.mysema.query.types.Predicate)
	 */
	@Override
	public List<T> findAll(Predicate predicate) {
		return createQuery(predicate).select(path).fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QuerydslPredicateExecutor#findAll(com.mysema.query.types.Predicate, com.mysema.query.types.OrderSpecifier<?>[])
	 */
	@Override
	public List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
		return executeSorted(createQuery(predicate).select(path), orders);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QuerydslPredicateExecutor#findAll(com.mysema.query.types.Predicate, org.springframework.data.domain.Sort)
	 */
	@Override
	public List<T> findAll(Predicate predicate, Sort sort) {

		Assert.notNull(sort, "Sort must not be null!");

		return executeSorted(createQuery(predicate).select(path), sort);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QuerydslPredicateExecutor#findAll(com.mysema.query.types.OrderSpecifier[])
	 */
	@Override
	public List<T> findAll(OrderSpecifier<?>... orders) {

		Assert.notNull(orders, "Order specifiers must not be null!");

		return executeSorted(createQuery(new Predicate[0]).select(path), orders);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QuerydslPredicateExecutor#findAll(com.querydsl.core.types.Predicate, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<T> findAll(Predicate predicate, Pageable pageable) {

		Assert.notNull(pageable, "Pageable must not be null!");

		JPQLQuery<?> countQuery = createCountQuery(predicate);
		JPQLQuery<T> query = querydsl.applyPagination(pageable, createQuery(predicate).select(path));

		return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#count(com.mysema.query.types.Predicate)
	 */
	@Override
	public long count(Predicate predicate) {
		return createQuery(predicate).fetchCount();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#exists(com.mysema.query.types.Predicate)
	 */
	@Override
	public boolean exists(Predicate predicate) {
		return createQuery(predicate).fetchCount() > 0;
	}

	/**
	 * Creates a new {@link JPQLQuery} for the given {@link Predicate}.
	 *
	 * @param predicate
	 * @return the Querydsl {@link JPQLQuery}.
	 */
	protected JPQLQuery<?> createQuery(Predicate... predicate) {

		AbstractJPAQuery<?, ?> query = doCreateQuery(getQueryHints().withFetchGraphs(entityManager), predicate);

		CrudMethodMetadata metadata = getRepositoryMethodMetadata();

		if (metadata == null) {
			return query;
		}

		LockModeType type = metadata.getLockModeType();
		return type == null ? query : query.setLockMode(type);
	}

	/**
	 * Creates a new {@link JPQLQuery} count query for the given {@link Predicate}.
	 *
	 * @param predicate, can be {@literal null}.
	 * @return the Querydsl count {@link JPQLQuery}.
	 */
	protected JPQLQuery<?> createCountQuery(@Nullable Predicate... predicate) {
		return doCreateQuery(getQueryHints(), predicate);
	}

	private AbstractJPAQuery<?, ?> doCreateQuery(QueryHints hints, @Nullable Predicate... predicate) {

		AbstractJPAQuery<?, ?> query = querydsl.createQuery(path);

		if (predicate != null) {
			query = query.where(predicate);
		}

		hints.forEach(query::setHint);

		return query;
	}

	/**
	 * Executes the given {@link JPQLQuery} after applying the given {@link OrderSpecifier}s.
	 *
	 * @param query  must not be {@literal null}.
	 * @param orders must not be {@literal null}.
	 * @return
	 */
	private List<T> executeSorted(JPQLQuery<T> query, OrderSpecifier<?>... orders) {
		return executeSorted(query, new QSort(orders));
	}

	/**
	 * Executes the given {@link JPQLQuery} after applying the given {@link Sort}.
	 *
	 * @param query must not be {@literal null}.
	 * @param sort  must not be {@literal null}.
	 * @return
	 */
	private List<T> executeSorted(JPQLQuery<T> query, Sort sort) {
		return querydsl.applySorting(sort, query).fetch();
	}
}