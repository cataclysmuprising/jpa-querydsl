package com.github.cataclysmuprising.jpa.service;

import com.github.cataclysmuprising.jpa.criteria.ContactInfoCriteria;
import com.github.cataclysmuprising.jpa.dto.ContactInfoDTO;
import org.springframework.data.domain.Pageable;

public interface ContactInfoService {

	ContactInfoDTO findById(Long id);

	ContactInfoDTO findByCriteria(ContactInfoCriteria criteria);

	Iterable<ContactInfoDTO> findAllByCriteria(ContactInfoCriteria criteria, Pageable pager);

	ContactInfoDTO create(ContactInfoDTO newEntry);

	Iterable<ContactInfoDTO> createAll(Iterable<ContactInfoDTO> dtos);

	ContactInfoDTO update(ContactInfoDTO updateEntry);

	long update(ContactInfoDTO updateDTO, ContactInfoCriteria criteria);

	ContactInfoDTO delete(Long id);
}
