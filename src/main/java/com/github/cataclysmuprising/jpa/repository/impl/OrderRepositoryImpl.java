package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.OrderCriteria;
import com.github.cataclysmuprising.jpa.entity.OrderEntity;
import com.github.cataclysmuprising.jpa.entity.QOrderEntity;
import com.github.cataclysmuprising.jpa.repository.OrderRepository;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepositoryImpl;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

import javax.persistence.EntityManager;

import static com.github.cataclysmuprising.jpa.config.PrimaryPersistenceContext.ENTITY_MANAGER_FACTORY;

public class OrderRepositoryImpl extends AbstractRepositoryImpl<OrderEntity, OrderCriteria, Long> implements OrderRepository {
	private final QOrderEntity qEntity = QOrderEntity.orderEntity;

	public OrderRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(OrderEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(OrderEntity entity, OrderCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(OrderCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
