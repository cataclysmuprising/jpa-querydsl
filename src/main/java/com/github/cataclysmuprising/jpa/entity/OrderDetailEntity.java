package com.github.cataclysmuprising.jpa.entity;

import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mjr_order_detail")
@Getter
@Setter
@ToString(callSuper = true)
public class OrderDetailEntity extends AbstractEntity<QOrderDetailEntity> {
	@NotNull
	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@NotNull
	@Column(name = "product_id", nullable = false)
	private Long productId;

	@NotNull
	@Min(1)
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@NotNull
	@Column(name = "order_price", nullable = false)
	private Double orderPrice;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QOrderDetailEntity qEntity) {

	}
}
