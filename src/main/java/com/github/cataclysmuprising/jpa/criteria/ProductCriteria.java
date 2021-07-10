package com.github.cataclysmuprising.jpa.criteria;

import com.github.cataclysmuprising.jpa.entity.ProductEntity;
import com.github.cataclysmuprising.jpa.entity.QProductEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ProductCriteria extends AbstractCriteria {

	private String productCode;
	private String productName;
	private String category;
	private Double currentPriceFrom;
	private Double currentPriceTo;
	private ProductEntity.Status status;
	private String description;

	@Override
	public Predicate getFilter() {
		QProductEntity qEntity = QProductEntity.productEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (StringUtils.isNotBlank(productCode)) {
			predicate.and(qEntity.productCode.eq(productCode));
		}
		if (StringUtils.isNotBlank(productName)) {
			predicate.and(qEntity.productName.eq(productName));
		}
		if (StringUtils.isNotBlank(category)) {
			predicate.and(qEntity.category.eq(category));
		}
		if (currentPriceFrom != null) {
			predicate.and(qEntity.currentPrice.gt(currentPriceFrom));
		}
		if (currentPriceTo != null) {
			predicate.and(qEntity.currentPrice.loe(currentPriceTo));
		}
		if (status != null) {
			predicate.and(qEntity.status.eq(status));
		}
		// define for keyword search
		if (StringUtils.isNotBlank(keyword)) {
			// @formatter:off
			predicate.and(
					qEntity.productCode.containsIgnoreCase(keyword)
					.or(qEntity.productName.containsIgnoreCase(keyword))
					.or(qEntity.category.containsIgnoreCase(keyword))
					.or(qEntity.description.containsIgnoreCase(keyword))
			);
			// @formatter:on
		}
		return predicate;
	}
}
