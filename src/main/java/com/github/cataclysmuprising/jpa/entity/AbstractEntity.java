package com.github.cataclysmuprising.jpa.entity;

import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class AbstractEntity<EntityPath> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "record_reg_id", nullable = false)
	private Long recordRegId;

	@Column(name = "record_reg_date", nullable = false)
	private LocalDateTime recordRegDate = LocalDateTime.now();

	@NotNull
	@Column(name = "record_upd_id", nullable = false)
	private Long recordUpdId;

	@Column(name = "record_upd_date", nullable = false)
	private LocalDateTime recordUpdDate = LocalDateTime.now();

	public abstract void mapForUpdate(JPAUpdateClause jpaUpdateClause, EntityPath qEntity);
}
