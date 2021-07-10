package com.github.cataclysmuprising.jpa.entity;

import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mjr_thumbnail")
@Getter
@Setter
@ToString(callSuper = true)
public class ThumbnailEntity extends AbstractEntity<QThumbnailEntity> {
	@NotBlank
	@Max(255)
	@Column(name = "original_name", nullable = false)
	private String originalName;

	@NotBlank
	@Max(150)
	@Column(name = "file_name", nullable = false, unique = true)
	private String fileName;

	@NotBlank
	@Column(name = "file_path", nullable = false)
	private String filePath;

	@NotBlank
	@Max(20)
	@Column(name = "file_size", nullable = false)
	private String fileSize;

	@NotNull
	@Column(name = "thumbnail_order", nullable = false)
	private Integer thumbnailOrder;

	@Override
	public void mapForUpdate(JPAUpdateClause jpaUpdateClause, QThumbnailEntity qEntity) {

	}
}
