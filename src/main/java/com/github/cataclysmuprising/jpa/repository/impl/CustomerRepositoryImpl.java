package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.CustomerCriteria;
import com.github.cataclysmuprising.jpa.entity.CustomerEntity;
import com.github.cataclysmuprising.jpa.entity.QCustomerEntity;
import com.github.cataclysmuprising.jpa.repository.CustomerRepository;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepositoryImpl;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.github.cataclysmuprising.jpa.config.PrimaryPersistenceContext.ENTITY_MANAGER_FACTORY;

@Repository
public class CustomerRepositoryImpl extends AbstractRepositoryImpl<CustomerEntity, CustomerCriteria, Long> implements CustomerRepository {

	private final QCustomerEntity qEntity = QCustomerEntity.customerEntity;

	public CustomerRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(CustomerEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(CustomerEntity entity, CustomerCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(CustomerCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
