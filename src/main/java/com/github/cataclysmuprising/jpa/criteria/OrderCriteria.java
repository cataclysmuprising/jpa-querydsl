package com.github.cataclysmuprising.jpa.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cataclysmuprising.jpa.entity.OrderEntity;
import com.github.cataclysmuprising.jpa.entity.QOrderEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class OrderCriteria extends AbstractCriteria {

	private Long customerId;
	private String orderNumber;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime orderDateFrom;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime orderDateTo;
	private OrderEntity.Status status;
	private String remark;

	@Override
	public Predicate getFilter() {
		QOrderEntity qEntity = QOrderEntity.orderEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (customerId != null) {
			predicate.and(qEntity.customerId.eq(customerId));
		}
		if (StringUtils.isNotBlank(orderNumber)) {
			predicate.and(qEntity.orderNumber.eq(orderNumber));
		}
		if (orderDateFrom != null) {
			predicate.and(qEntity.orderDate.gt(orderDateFrom));
		}
		if (orderDateTo != null) {
			predicate.and(qEntity.orderDate.loe(orderDateTo));
		}
		if (status != null) {
			predicate.and(qEntity.status.eq(status));
		}
		// define for keyword search
		if (StringUtils.isNotBlank(keyword)) {
			// @formatter:off
			predicate.and(
					qEntity.orderNumber.containsIgnoreCase(keyword)
					.or(qEntity.remark.containsIgnoreCase(keyword))
			);
			// @formatter:on
		}
		return predicate;
	}
}
