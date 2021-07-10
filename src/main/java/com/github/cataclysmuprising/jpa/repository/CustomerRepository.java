package com.github.cataclysmuprising.jpa.repository;

import com.github.cataclysmuprising.jpa.criteria.CustomerCriteria;
import com.github.cataclysmuprising.jpa.entity.CustomerEntity;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepository;

public interface CustomerRepository extends AbstractRepository<CustomerEntity, CustomerCriteria, Long> {}
