package com.github.cataclysmuprising.jpa.criteria;

import com.github.cataclysmuprising.jpa.entity.QContactInfoEntity;
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
public class ContactInfoCriteria extends AbstractCriteria {
	private String contactPhone;
	private String contactEmail;
	private String country;
	private String city;
	private String street;
	private String postalCode;

	@Override
	public Predicate getFilter() {
		QContactInfoEntity qEntity = QContactInfoEntity.contactInfoEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (StringUtils.isNotBlank(contactPhone)) {
			predicate.and(qEntity.contactPhone.eq(contactPhone));
		}
		if (StringUtils.isNotBlank(contactEmail)) {
			predicate.and(qEntity.contactEmail.eq(contactEmail));
		}
		if (StringUtils.isNotBlank(country)) {
			predicate.and(qEntity.country.eq(country));
		}
		if (StringUtils.isNotBlank(city)) {
			predicate.and(qEntity.city.eq(city));
		}
		if (StringUtils.isNotBlank(postalCode)) {
			predicate.and(qEntity.postalCode.eq(postalCode));
		}
		// define for keyword search
		if (StringUtils.isNotBlank(keyword)) {
			// @formatter:off
			predicate.and(
					qEntity.contactPhone.containsIgnoreCase(keyword)
					 .or(qEntity.contactEmail.containsIgnoreCase(keyword))
					 .or(qEntity.country.containsIgnoreCase(keyword))
					 .or(qEntity.city.containsIgnoreCase(keyword))
					 .or(qEntity.postalCode.containsIgnoreCase(keyword))
					 .or(qEntity.address.containsIgnoreCase(keyword))
			);
			// @formatter:on
		}
		return predicate;
	}
}
