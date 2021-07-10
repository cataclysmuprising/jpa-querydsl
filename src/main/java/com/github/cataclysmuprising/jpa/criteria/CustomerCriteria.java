package com.github.cataclysmuprising.jpa.criteria;

import com.github.cataclysmuprising.jpa.entity.CustomerEntity;
import com.github.cataclysmuprising.jpa.entity.QCustomerEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class CustomerCriteria extends AbstractCriteria {
	private Long contactInfoId;
	private String customerName;
	private String email;
	private Double balanceFrom;
	private Double balanceTo;
	private CustomerEntity.Status status;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Predicate getFilter() {
		QCustomerEntity qEntity = QCustomerEntity.customerEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (contactInfoId != null) {
			predicate.and(qEntity.contactInfoId.eq(contactInfoId));
		}
		if (StringUtils.isNotBlank(customerName)) {
			predicate.and(qEntity.customerName.eq(customerName));
		}
		if (StringUtils.isNotBlank(email)) {
			predicate.and(qEntity.email.eq(email));
		}
		if (balanceFrom != null) {
			predicate.and(qEntity.balance.gt(balanceFrom));
		}
		if (balanceTo != null) {
			predicate.and(qEntity.balance.loe(balanceTo));
		}
		if (status != null) {
			predicate.and(qEntity.status.eq(status));
		}
		// define for keyword search
		if (StringUtils.isNotBlank(keyword)) {
			// @formatter:off
			predicate.and(
					qEntity.customerName.containsIgnoreCase(keyword)
					.or(qEntity.email.containsIgnoreCase(keyword))
			);
			// @formatter:on
		}
		return predicate;
	}
}
