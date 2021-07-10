package com.github.cataclysmuprising.jpa.unitTests;

import com.github.cataclysmuprising.jpa.CommonTestBase;
import com.github.cataclysmuprising.jpa.criteria.ContactInfoCriteria;
import com.github.cataclysmuprising.jpa.entity.ContactInfoEntity;
import com.github.cataclysmuprising.jpa.repository.ContactInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactInfoUnitTest extends CommonTestBase {
	private static final Logger testLogger = LogManager.getLogger("testLogs." + ContactInfoUnitTest.class.getName());

	@Autowired
	private ContactInfoRepository repository;

	@Test
	public void findAll() {
		repository.findAll(new ContactInfoCriteria().getFilter()).iterator().forEachRemaining(testLogger::info);
	}

	@Test
	public void findOneById() {
		Long findId = 1000L;
		repository.findById(findId).ifPresent(testLogger::info);
	}

	@Test
	public void findOneByCriteria() {
		ContactInfoCriteria criteria = new ContactInfoCriteria();
		criteria.setId(1000L);
		criteria.setContactPhone("(01)099152591");
		criteria.setContactEmail("company1@example.com");
		criteria.setCountry("Myanmar");
		criteria.setCity("Yangon");
		criteria.setPostalCode("11171");
		criteria.setKeyword("Anawrahta");
		repository.findOne(criteria.getFilter()).ifPresent(testLogger::info);
	}

	@Test
	public void findAllByCriteria() {
		ContactInfoCriteria criteria = new ContactInfoCriteria();
		criteria.setKeyword("example.com");
		repository.findAll(criteria.getFilter()).iterator().forEachRemaining(testLogger::info);
	}

	@Test
	public void findAllByCriteriaWithSort() {
		ContactInfoCriteria criteria = new ContactInfoCriteria();
		criteria.setKeyword("example.com");
		// multiple sorting
		// Sort sort = criteria.getSort("country", "asc").and(criteria.getSort("city", "desc"));
		repository.findAll(criteria.getFilter(), criteria.getSort("id", "desc")).iterator().forEachRemaining(testLogger::info);
	}

	@Test
	public void findAllByCriteriaWithPagingAndSort() {
		ContactInfoCriteria criteria = new ContactInfoCriteria();
		criteria.setKeyword("example.com");
		repository.findAll(criteria.getFilter(), criteria.getPager(1, 2, "id", "desc")).iterator().forEachRemaining(testLogger::info);
	}

	@Test
	public void insertSingle() {
		ContactInfoEntity entity = new ContactInfoEntity();
		entity.setContactPhone("09111111111");
		entity.setContactEmail("contact1@example.com");
		entity.setCountry("Myanmar");
		entity.setCity("Yangon");
		entity.setPostalCode("00123");
		entity.setAddress("No:123 , Room 3 : 5th Floor");
		entity.setRecordRegId(TEST_CREATE_USER_ID);
		entity.setRecordUpdId(TEST_UPDATE_USER_ID);

		entity = repository.save(entity);

		testLogger.info("Inserted ID # " + entity.getId());
	}

	@Test
	public void insertList() {
		List<ContactInfoEntity> entityList = new ArrayList<>();

		ContactInfoEntity entity1 = new ContactInfoEntity();
		entity1.setContactPhone("09222222222");
		entity1.setContactEmail("contact2@example.com");
		entity1.setCountry("Myanmar");
		entity1.setCity("Mandalay");
		entity1.setPostalCode("000871");
		entity1.setAddress("No:234 , Room 2 : 7th Floor");
		entity1.setRecordRegId(TEST_CREATE_USER_ID);
		entity1.setRecordUpdId(TEST_UPDATE_USER_ID);

		entityList.add(entity1);

		ContactInfoEntity entity2 = new ContactInfoEntity();
		entity2.setContactPhone("09333333333");
		entity2.setContactEmail("contact3@example.com");
		entity2.setCountry("Myanmar");
		entity2.setCity("Taunggyi");
		entity2.setPostalCode("006374");
		entity2.setAddress("No:789 , Room 1 : 2nd Floor");
		entity2.setRecordRegId(TEST_CREATE_USER_ID);
		entity2.setRecordUpdId(TEST_UPDATE_USER_ID);

		entityList.add(entity2);

		entityList = repository.saveAll(entityList);

		entityList.forEach(entity -> testLogger.info("Inserted Entity => " + entity));
	}

	@Test
	public void updateById() {
		Long updateId = 1000L;
		repository.findById(updateId).ifPresent(oldEntity -> {
			testLogger.info("Old Entity Informations ==> " + oldEntity);
			oldEntity.setContactPhone("09555555555");
			oldEntity.setContactEmail("contact5@example.com");
			oldEntity.setCountry("Myanmar");
			oldEntity.setCity("Bago");
			oldEntity.setPostalCode("005753");
			oldEntity.setAddress("No:6751 , Room 4 : 1st Floor");
			oldEntity.setRecordUpdId(TEST_UPDATE_USER_ID);
			//we don't need to flush if we don't fetch within a transaction to view
			repository.flush();

			repository.findById(oldEntity.getId()).ifPresent(testLogger::info);
		});
	}

	@Test
	public void updateByCriteria() {
		ContactInfoCriteria criteria = new ContactInfoCriteria();
		criteria.setContactPhone("(01)099152591");
		criteria.setContactEmail("company1@example.com");

		ContactInfoEntity updateEntity = new ContactInfoEntity();
		updateEntity.setCity("Myae Ni Gone (north) Ward");
		updateEntity.setPostalCode("111342");
		updateEntity.setAddress("(Rm 57/58), 1st Flr, Bargayar Rd.");

		long effectedRows = repository.update(updateEntity, criteria);
		testLogger.info("Effected Row Count ==> " + effectedRows);
		//we don't need to flush if we don't fetch within a transaction to view
		repository.flush();
		repository.findOne(criteria.getFilter()).ifPresent(testLogger::info);
	}

	@Test
	public void deleteById() {
		Long deleteId = 1000L;
		repository.deleteById(deleteId);
		repository.findById(deleteId).ifPresent(testLogger::info);
	}

	@Test
	public void deleteByObject() {
		Long deleteObjectId = 1000L;
		Optional<ContactInfoEntity> deleteEntity = repository.findById(deleteObjectId);
		deleteEntity.ifPresent(repository::delete);
		repository.findById(deleteObjectId).ifPresent(testLogger::info);
	}

	@Test
	public void deleteByCriteria() {
		ContactInfoCriteria criteria = new ContactInfoCriteria();
		criteria.setContactPhone("(01)099152591");
		criteria.setContactEmail("company1@example.com");

		long effectedRows = repository.delete(criteria);
		testLogger.info("Effected Row Count ==> " + effectedRows);
	}
}
