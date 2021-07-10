package com.github.cataclysmuprising.jpa.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.cataclysmuprising.jpa.dto.AbstractDTO;
import com.github.cataclysmuprising.jpa.entity.AbstractEntity;
import com.github.cataclysmuprising.jpa.exception.ValidationFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectMapperUtil {
	private static final Logger logger = LogManager.getLogger(ObjectMapperUtil.class);

	private static final ObjectMapper objectMapper;
	private static final Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static <E extends AbstractEntity, D extends AbstractDTO> D mapEntityIntoDTO(E entity, Class<D> clazz) {
		return objectMapper.convertValue(entity, clazz);
	}

	public static <E extends AbstractEntity> E updateEntity(E source, E target) {
		// keep parent class attributes bcos these may cause null while update
		source.setId(target.getId());
		source.setRecordRegId(target.getRecordRegId());
		source.setRecordRegDate(target.getRecordRegDate());
		source.setRecordUpdId(target.getRecordUpdId());
		source.setRecordUpdDate(target.getRecordUpdDate());
		try {
			return objectMapper.updateValue(target, source);
		}
		catch (JsonMappingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <E extends AbstractEntity, D extends AbstractDTO> Iterable<D> mapEntityIntoDTO(Iterable<E> entities, Class<D> clazz) {
		List<D> dtos = new ArrayList<>();
		entities.forEach(e -> dtos.add(mapEntityIntoDTO(e, clazz)));
		return dtos;
	}

	public static <D extends AbstractDTO, E extends AbstractEntity> E mapDTOIntoEntity(D dto, Class<E> clazz) {
		E obj = objectMapper.convertValue(dto, clazz);
		Set<ConstraintViolation<E>> constraintViolations = validator.validate(obj);
		if (constraintViolations.size() > 0) {
			throw new ValidationFailedException(constraintViolations);
		}
		return obj;
	}

	public static <D extends AbstractDTO, E extends AbstractEntity> Iterable<E> mapDTOIntoEntity(Iterable<D> dtos, Class<E> clazz) {
		List<E> entities = new ArrayList<>();
		dtos.forEach(dto -> entities.add(mapDTOIntoEntity(dto, clazz)));
		return entities;
	}

	public static <T, D extends AbstractDTO> void showEntriesOfCollection(Iterable<D> results) {
		if (results != null) {
			results.forEach(result -> logger.debug(" >>> " + result.toString()));
		}
	}
}
