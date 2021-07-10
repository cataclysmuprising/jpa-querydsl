package com.github.cataclysmuprising.jpa.criteria;

import com.github.cataclysmuprising.jpa.entity.QThumbnailEntity;
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
public class ThumbnailCriteria extends AbstractCriteria {
	private String originalName;
	private String fileName;
	private String filePath;
	private String fileSize;
	private Integer thumbnailOrder;

	@Override
	public Predicate getFilter() {
		QThumbnailEntity qEntity = QThumbnailEntity.thumbnailEntity;
		BooleanBuilder predicate = getCommonFilter(qEntity._super);

		if (StringUtils.isNotBlank(originalName)) {
			predicate.and(qEntity.originalName.eq(originalName));
		}
		if (StringUtils.isNotBlank(fileName)) {
			predicate.and(qEntity.fileName.eq(fileName));
		}
		if (StringUtils.isNotBlank(filePath)) {
			predicate.and(qEntity.filePath.eq(filePath));
		}
		if (StringUtils.isNotBlank(fileSize)) {
			predicate.and(qEntity.fileSize.eq(fileSize));
		}
		if (thumbnailOrder != null) {
			predicate.and(qEntity.thumbnailOrder.eq(thumbnailOrder));
		}
		// define for keyword search
		if (StringUtils.isNotBlank(keyword)) {
			// @formatter:off
			predicate.and(
					qEntity.originalName.containsIgnoreCase(keyword)
					.or(qEntity.filePath.containsIgnoreCase(keyword))
			);
			// @formatter:on
		}
		return predicate;
	}
}
