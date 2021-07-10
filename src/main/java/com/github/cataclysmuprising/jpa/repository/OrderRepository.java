package com.github.cataclysmuprising.jpa.repository;

import com.github.cataclysmuprising.jpa.criteria.OrderCriteria;
import com.github.cataclysmuprising.jpa.entity.OrderEntity;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepository;

public interface OrderRepository extends AbstractRepository<OrderEntity, OrderCriteria, Long> {}
