package com.github.cataclysmuprising.jpa.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public abstract class AbstractDTO {
	private Long id;

	@Setter(AccessLevel.NONE)
	private Long recordRegId;

	@Setter(AccessLevel.NONE)
	private LocalDateTime recordRegTime;

	@Setter(AccessLevel.NONE)
	private Long recordUpdId;

	@Setter(AccessLevel.NONE)
	private LocalDateTime recordUpdTime;
}
