package com.github.cataclysmuprising.jpa.repository;

import com.github.cataclysmuprising.jpa.criteria.ProductCriteria;
import com.github.cataclysmuprising.jpa.entity.ProductEntity;
import com.github.cataclysmuprising.jpa.repository.base.AbstractRepository;

public interface ProductRepository extends AbstractRepository<ProductEntity, ProductCriteria, Long> {}
