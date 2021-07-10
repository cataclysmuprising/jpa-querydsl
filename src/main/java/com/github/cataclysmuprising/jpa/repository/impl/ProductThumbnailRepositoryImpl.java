package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.ProductThumbnailCriteria;
import com.github.cataclysmuprising.jpa.entity.ProductThumbnailEntity;
import com.github.cataclysmuprising.jpa.entity.QProductThumbnailEntity;
import com.github.cataclysmuprising.jpa.repository.ProductThumbnailRepository;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepositoryImpl;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

import javax.persistence.EntityManager;

import static com.github.cataclysmuprising.jpa.config.PrimaryPersistenceContext.ENTITY_MANAGER_FACTORY;

public class ProductThumbnailRepositoryImpl extends AbstractRepositoryImpl<ProductThumbnailEntity, ProductThumbnailCriteria, Long> implements ProductThumbnailRepository {
	private final QProductThumbnailEntity qEntity = QProductThumbnailEntity.productThumbnailEntity;

	public ProductThumbnailRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(ProductThumbnailEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(ProductThumbnailEntity entity, ProductThumbnailCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(ProductThumbnailCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
