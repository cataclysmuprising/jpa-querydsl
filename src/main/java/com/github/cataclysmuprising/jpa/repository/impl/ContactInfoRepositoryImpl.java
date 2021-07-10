package com.github.cataclysmuprising.jpa.repository.impl;

import com.github.cataclysmuprising.jpa.criteria.ContactInfoCriteria;
import com.github.cataclysmuprising.jpa.entity.ContactInfoEntity;
import com.github.cataclysmuprising.jpa.entity.QContactInfoEntity;
import com.github.cataclysmuprising.jpa.repository.ContactInfoRepository;
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
public class ContactInfoRepositoryImpl extends AbstractRepositoryImpl<ContactInfoEntity, ContactInfoCriteria, Long> implements ContactInfoRepository {

	private final QContactInfoEntity qEntity = QContactInfoEntity.contactInfoEntity;

	public ContactInfoRepositoryImpl(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<>(ContactInfoEntity.class, entityManager.getMetamodel()), entityManager);
	}

	@Override
	public long update(ContactInfoEntity entity, ContactInfoCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPAUpdateClause jpaUpdateClause = queryFactory.update(qEntity);
		entity.mapForUpdate(jpaUpdateClause, qEntity);
		return jpaUpdateClause.where(criteria.getFilter()).execute();
	}

	@Override
	public long delete(ContactInfoCriteria criteria) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		JPADeleteClause jpaDeleteClause = queryFactory.delete(qEntity);
		return jpaDeleteClause.where(criteria.getFilter()).execute();
	}
}
