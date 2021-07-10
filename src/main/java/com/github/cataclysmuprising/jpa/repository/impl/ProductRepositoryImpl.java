package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.ProductCriteria;
import com.github.cataclysmuprising.jpa.entity.ProductEntity;
import com.github.cataclysmuprising.jpa.entity.QProductEntity;
import com.github.cataclysmuprising.jpa.repository.ProductRepository;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepositoryImpl;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

import javax.persistence.EntityManager;

import static com.github.cataclysmuprising.jpa.config.PrimaryPersistenceContext.ENTITY_MANAGER_FACTORY;

public class ProductRepositoryImpl extends AbstractRepositoryImpl<ProductEntity, ProductCriteria, Long> implements ProductRepository {
	private final QProductEntity qEntity = QProductEntity.productEntity;

	public ProductRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(ProductEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(ProductEntity entity, ProductCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(ProductCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
