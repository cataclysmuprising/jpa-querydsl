package com.github.cataclysmuprising.jpa.entity;

import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mjr_product_x_thumbnail")
@Getter
@Setter
@ToString(callSuper = true)
public class ProductThumbnailEntity extends AbstractEntity<QProductThumbnailEntity> {
	@NotNull
	@Column(name = "product_id", nullable = false)
	private Long productId;

	@NotNull
	@Column(name = "thumbnail_id", nullable = false)
	private Long thumbnailId;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QProductThumbnailEntity qEntity) {

	}
}
