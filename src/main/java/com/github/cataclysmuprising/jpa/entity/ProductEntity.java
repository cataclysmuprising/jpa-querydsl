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

@Entity
@Table(name = "mjr_product")
@Getter
@Setter
@ToString(callSuper = true)
public class ProductEntity extends AbstractEntity<QProductEntity> {

	@NotBlank
	@Max(36)
	@Column(name = "product_code", nullable = false)
	private String productCode;

	@NotBlank
	@Max(200)
	@Column(name = "product_name", nullable = false)
	private String productName;

	@NotBlank
	@Max(50)
	@Column(name = "category", nullable = false)
	private String category;

	@NotNull
	@Column(name = "current_price", nullable = false)
	private Double currentPrice;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable = false)
	private Status status;

	@Column(name = "description")
	private String description;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QProductEntity qEntity) {

	}

	public enum Status {
		// @formatter:off
		AVAILABLE("Available"),
		OUT_OF_STOCK("Out of Stock"),
		UNAVAILABLE("Unavailable");
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
