package com.github.cataclysmuprising.jpa.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "mjr_order")
@Getter
@Setter
@ToString(callSuper = true)
public class OrderEntity extends AbstractEntity<QOrderEntity> {

	@NotBlank
	@Max(36)
	@Column(name = "order_number", nullable = false, unique = true)
	private String orderNumber;

	@NotNull
	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@NotNull
	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate = LocalDateTime.now();

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable = false)
	private Status status;

	@Column(name = "remark")
	private String remark;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QOrderEntity qEntity) {

	}

	public enum Status {
		// @formatter:off
		PENDING("Pending"),
		COMPLETED("Completed"),
		REJECTED("Rejected");
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
