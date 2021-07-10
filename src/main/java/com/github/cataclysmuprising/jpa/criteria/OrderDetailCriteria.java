package com.github.cataclysmuprising.jpa.criteria;

import com.github.cataclysmuprising.jpa.entity.QOrderDetailEntity;
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
public class OrderDetailCriteria extends AbstractCriteria {
	private Long orderId;
	private Long productId;
	private Integer quantity;
	private Double orderPriceFrom;
	private Double orderPriceTo;

	@Override
	public Predicate getFilter() {
		QOrderDetailEntity qEntity = QOrderDetailEntity.orderDetailEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (orderId != null) {
			predicate.and(qEntity.orderId.eq(orderId));
		}
		if (productId != null) {
			predicate.and(qEntity.productId.eq(productId));
		}
		if (quantity != null) {
			predicate.and(qEntity.quantity.eq(quantity));
		}
		if (orderPriceFrom != null) {
			predicate.and(qEntity.orderPrice.gt(orderPriceFrom));
		}
		if (orderPriceTo != null) {
			predicate.and(qEntity.orderPrice.loe(orderPriceTo));
		}

		return predicate;
	}
}
