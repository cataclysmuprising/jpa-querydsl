package com.github.cataclysmuprising.jpa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class StudentDTO extends AbstractDTO {
	private String name;
}
