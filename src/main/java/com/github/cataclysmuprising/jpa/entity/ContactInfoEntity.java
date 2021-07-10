package com.github.cataclysmuprising.jpa.entity;

import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "mjr_contact_info")
@Getter
@Setter
@ToString(callSuper = true)
public class ContactInfoEntity extends AbstractEntity<QContactInfoEntity> {
	@NotBlank
	@Max(30)
	@Column(name = "contact_phone", nullable = false)
	private String contactPhone;

	@NotBlank
	@Max(100)
	@Column(name = "contact_email", nullable = false)
	private String contactEmail;

	@NotBlank
	@Max(100)
	@Column(name = "country", nullable = false)
	private String country;

	@NotBlank
	@Max(150)
	@Column(name = "city", nullable = false)
	private String city;

	@NotBlank
	@Max(100)
	@Column(name = "postal_code", nullable = false)
	private String postalCode;

	@Column(name = "address")
	private String address;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QContactInfoEntity qEntity) {
		if (StringUtils.isNotBlank(contactPhone)) {
			jpaUpdateClause.set(qEntity.contactPhone, contactPhone);
		}
		if (StringUtils.isNotBlank(contactEmail)) {
			jpaUpdateClause.set(qEntity.contactEmail, contactEmail);
		}
		if (StringUtils.isNotBlank(country)) {
			jpaUpdateClause.set(qEntity.country, country);
		}
		if (StringUtils.isNotBlank(city)) {
			jpaUpdateClause.set(qEntity.city, city);
		}
		if (StringUtils.isNotBlank(postalCode)) {
			jpaUpdateClause.set(qEntity.postalCode, postalCode);
		}
		if (StringUtils.isNotBlank(address)) {
			jpaUpdateClause.set(qEntity.address, address);
		}
	}
}
