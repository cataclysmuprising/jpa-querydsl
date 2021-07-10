package com.github.cataclysmuprising.jpa.repository;

import com.github.cataclysmuprising.jpa.criteria.OrderDetailCriteria;
import com.github.cataclysmuprising.jpa.entity.OrderDetailEntity;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepository;

public interface OrderDetailRepository extends AbstractRepository<OrderDetailEntity, OrderDetailCriteria, Long> {}
