package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.ThumbnailCriteria;
import com.github.cataclysmuprising.jpa.entity.QThumbnailEntity;
import com.github.cataclysmuprising.jpa.entity.ThumbnailEntity;
import com.github.cataclysmuprising.jpa.repository.ThumbnailRepository;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepositoryImpl;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

import javax.persistence.EntityManager;

import static com.github.cataclysmuprising.jpa.config.PrimaryPersistenceContext.ENTITY_MANAGER_FACTORY;

public class ThumbnailRepositoryImpl extends AbstractRepositoryImpl<ThumbnailEntity, ThumbnailCriteria, Long> implements ThumbnailRepository {
	private final QThumbnailEntity qEntity = QThumbnailEntity.thumbnailEntity;

	public ThumbnailRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(ThumbnailEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(ThumbnailEntity entity, ThumbnailCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(ThumbnailCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
