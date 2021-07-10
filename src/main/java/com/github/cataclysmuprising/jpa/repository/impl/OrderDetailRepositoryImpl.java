package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.OrderDetailCriteria;
import com.github.cataclysmuprising.jpa.entity.OrderDetailEntity;
import com.github.cataclysmuprising.jpa.entity.QOrderDetailEntity;
import com.github.cataclysmuprising.jpa.repository.OrderDetailRepository;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepositoryImpl;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

import javax.persistence.EntityManager;

import static com.github.cataclysmuprising.jpa.config.PrimaryPersistenceContext.ENTITY_MANAGER_FACTORY;

public class OrderDetailRepositoryImpl extends AbstractRepositoryImpl<OrderDetailEntity, OrderDetailCriteria, Long> implements OrderDetailRepository {
	private final QOrderDetailEntity qEntity = QOrderDetailEntity.orderDetailEntity;

	public OrderDetailRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(OrderDetailEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(OrderDetailEntity entity, OrderDetailCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(OrderDetailCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
