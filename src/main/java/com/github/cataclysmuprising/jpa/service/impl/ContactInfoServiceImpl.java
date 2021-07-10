package com.github.cataclysmuprising.jpa.service.impl;

import com.github.cataclysmuprising.jpa.common.ObjectMapperUtil;
import com.github.cataclysmuprising.jpa.criteria.ContactInfoCriteria;
import com.github.cataclysmuprising.jpa.dto.ContactInfoDTO;
import com.github.cataclysmuprising.jpa.entity.ContactInfoEntity;
import com.github.cataclysmuprising.jpa.repository.ContactInfoRepository;
import com.github.cataclysmuprising.jpa.service.ContactInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

	private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + ContactInfoServiceImpl.class);

	private final ContactInfoRepository repository;

	@Autowired
	ContactInfoServiceImpl(ContactInfoRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	@Override
	public ContactInfoDTO findById(Long id) {
		serviceLogger.info("Finding student entry by using id: {}", id);

		ContactInfoEntity studentEntry = findEntryById(id);
		serviceLogger.info("Found student entry: {}", studentEntry);

		return ObjectMapperUtil.mapEntityIntoDTO(studentEntry, ContactInfoDTO.class);
	}

	@Transactional(readOnly = true)
	@Override
	public ContactInfoDTO findByCriteria(ContactInfoCriteria criteria) {
		serviceLogger.info("Finding student entry by using criteria : {}", criteria);

		Optional<ContactInfoEntity> entry = repository.findOne(criteria.getFilter());
		serviceLogger.info("Found student entry: {}", entry);

		return ObjectMapperUtil.mapEntityIntoDTO(entry.orElseThrow(EntityNotFoundException::new), ContactInfoDTO.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<ContactInfoDTO> findAllByCriteria(ContactInfoCriteria criteria, Pageable pager) {
		serviceLogger.info("Finding student entry by using criteria : {}", criteria);
		Iterable<ContactInfoEntity> entries;
		if (pager != null) {
			entries = repository.findAll(criteria.getFilter(), pager);
		}
		else {
			entries = repository.findAll(criteria.getFilter());
		}
		serviceLogger.info("Found student entry: {}", entries);

		return ObjectMapperUtil.mapEntityIntoDTO(entries, ContactInfoDTO.class);
	}

	@Transactional
	@Override
	public ContactInfoDTO create(ContactInfoDTO dto) {
		serviceLogger.info("Creating a new student entry by using information: {}", dto);

		ContactInfoEntity created = ObjectMapperUtil.mapDTOIntoEntity(dto, ContactInfoEntity.class);
		created = repository.save(created);
		serviceLogger.info("Created a new student entry: {}", created);

		return ObjectMapperUtil.mapEntityIntoDTO(created, ContactInfoDTO.class);
	}

	@Transactional
	@Override
	public Iterable<ContactInfoDTO> createAll(Iterable<ContactInfoDTO> dtos) {
		serviceLogger.info("Creating a new student entries by using information: - ");
		ObjectMapperUtil.showEntriesOfCollection(dtos);

		Iterable<ContactInfoEntity> entities = ObjectMapperUtil.mapDTOIntoEntity(dtos, ContactInfoEntity.class);
		entities = repository.saveAll(entities);

		return ObjectMapperUtil.mapEntityIntoDTO(entities, ContactInfoDTO.class);
	}

	@Transactional
	@Override
	public ContactInfoDTO update(ContactInfoDTO updateDTO) {
		serviceLogger.info("Updating the information of a student entry by using information: {}", updateDTO);

		ContactInfoEntity updateEntry = ObjectMapperUtil.mapDTOIntoEntity(updateDTO, ContactInfoEntity.class);
		ContactInfoEntity oldEntry = findEntryById(updateDTO.getId());
		ObjectMapperUtil.updateEntity(updateEntry, oldEntry);
		// We need to flush the changes or otherwise the returned object
		// doesn't contain the updated audit information.
		repository.flush();

		serviceLogger.info("Updated the information of the student entry: {}", oldEntry);
		return ObjectMapperUtil.mapEntityIntoDTO(oldEntry, ContactInfoDTO.class);
	}

	@Transactional
	@Override
	public long update(ContactInfoDTO updateDTO, ContactInfoCriteria criteria) {
		serviceLogger.info("Updating by criteria");
		ContactInfoEntity updateEntry = ObjectMapperUtil.mapDTOIntoEntity(updateDTO, ContactInfoEntity.class);
		return repository.update(updateEntry, criteria);
	}

	@Transactional
	@Override
	public ContactInfoDTO delete(Long id) {
		serviceLogger.info("Deleting a student entry with id: {}", id);

		ContactInfoEntity deleted = findEntryById(id);
		serviceLogger.debug("Found student entry: {}", deleted);

		repository.delete(deleted);
		serviceLogger.info("Deleted student entry: {}", deleted);

		return ObjectMapperUtil.mapEntityIntoDTO(deleted, ContactInfoDTO.class);
	}

	private ContactInfoEntity findEntryById(Long id) {
		Optional<ContactInfoEntity> studentResult = repository.findById(id);
		return studentResult.orElseThrow(EntityNotFoundException::new);
	}
}
