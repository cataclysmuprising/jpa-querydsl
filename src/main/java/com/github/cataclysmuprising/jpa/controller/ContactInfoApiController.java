package com.github.cataclysmuprising.jpa.controller;

import com.github.cataclysmuprising.jpa.criteria.ContactInfoCriteria;
import com.github.cataclysmuprising.jpa.dto.ContactInfoDTO;
import com.github.cataclysmuprising.jpa.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contact-info")
public class ContactInfoApiController {

	@Autowired
	ContactInfoService studentService;

	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody ContactInfoCriteria criteria) {
		ContactInfoDTO record = studentService.findByCriteria(criteria);
		return new ResponseEntity<>(record, HttpStatus.OK);
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<?> searchById(@PathVariable("id") long id) {
		ContactInfoDTO record = studentService.findById(id);
		if (record == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(record, HttpStatus.OK);
	}

	@PostMapping("/search/list")
	public ResponseEntity<?> searchList(@RequestBody ContactInfoCriteria criteria) {
		Iterable<ContactInfoDTO> records = studentService.findAllByCriteria(criteria, null);
		if (records == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(records, HttpStatus.OK);
	}
}
