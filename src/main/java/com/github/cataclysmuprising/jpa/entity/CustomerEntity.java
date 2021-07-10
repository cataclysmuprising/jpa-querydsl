package com.github.cataclysmuprising.jpa.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mjr_customer")
@Getter
@Setter
@ToString(callSuper = true)
public class CustomerEntity extends AbstractEntity<QCustomerEntity> {
	@NotBlank
	@Max(100)
	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@NotBlank
	@Email
	@Max(100)
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotBlank
	@Max(200)
	@Column(name = "password", nullable = false)
	private String password;

	@NotNull
	@Max(100)
	@Column(name = "balance", nullable = false)
	private Double balance;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable = false)
	private Status status;

	@NotNull
	@Column(name = "contact_info_id", nullable = false)
	private Long contactInfoId;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QCustomerEntity qEntity) {

	}

	public enum Status {
		// @formatter:off
		ACTIVE("Active"),
		TEMPORARY("Temporary"),
		BLOCKED("Blocked");
		// @formatter:on

		private final String definition;

		Status(String definition) {
			this.definition = definition;
		}

		public static Status getEnum(String value) {
			Status _enum = null;
			for (Status v : values()) {
				if (v.getDefinition().trim().equalsIgnoreCase(value)) {
					_enum = v;
					break;
				}
			}
			return _enum;
		}

		@JsonCreator
		public static Status forValue(String value) {
			return Status.getEnum(value);
		}

		@JsonValue
		public String getDefinition() {
			return definition;
		}

		@Override
		public String toString() {
			return definition;
		}
	}
}
