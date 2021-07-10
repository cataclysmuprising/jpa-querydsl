package com.github.cataclysmuprising.jpa.criteria;

import com.github.cataclysmuprising.jpa.entity.QProductThumbnailEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ProductThumbnailCriteria extends AbstractCriteria {
	private Long productId;
	private Long thumbnailId;

	@Override
	public Predicate getFilter() {
		QProductThumbnailEntity qEntity = QProductThumbnailEntity.productThumbnailEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (productId != null) {
			predicate.and(qEntity.productId.eq(productId));
		}
		if (thumbnailId != null) {
			predicate.and(qEntity.thumbnailId.eq(thumbnailId));
		}

		return predicate;
	}
}
