package com.github.cataclysmuprising.jpa.repository;

import com.github.cataclysmuprising.jpa.criteria.ContactInfoCriteria;
import com.github.cataclysmuprising.jpa.entity.ContactInfoEntity;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepository;

public interface ContactInfoRepository extends AbstractRepository<ContactInfoEntity, ContactInfoCriteria, Long> {}
