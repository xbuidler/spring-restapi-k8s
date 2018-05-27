package com.restbase.model.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restbase.model.domain.Test;
import com.restbase.model.repository.TestRepository;
import com.restbase.model.validator.ConstraintValidators;

@CacheConfig
@Service
@Transactional
public class TestService {

	private static final String ID_COULD_NOT_BE_FOUND_MSG = "Id %s could not be found";

	private static final Logger logger = LoggerFactory.getLogger(TestService.class);

	@Autowired
	private TestRepository testRepository;

	public TestService() {
		// default constructor
	}

	public List<Test> list() {
		logger.info("Listing Tests");
		return testRepository.findAll();

	}

	public void save(Test test) {
		logger.info("Listing Tests");
		testRepository.save(test);
	}
	
	public void updateByUUID(UUID id, Test test) {
		logger.info("Updating Test BY UUID");
		ConstraintValidators.checkIfParameterIsNull(id, "id");
		ConstraintValidators.checkIfParameterIsNull(test, "test");
		Test toUpdate = findByUuid(id);
		String message = String.format(ID_COULD_NOT_BE_FOUND_MSG, id);
		ConstraintValidators.checkNull(toUpdate, message);
		testRepository.save(toUpdate);
	}
	
	public Test findByPk(Long id) {
		logger.info("Finding Test");
		ConstraintValidators.checkIfParameterIsNull(id, "pk");
		Optional<Test> entity = testRepository.findById(id);
		return entity.orElse(null);
	}
	
	public Test findByUuid(UUID uuid) {
		logger.info("Finding Test By UUID");
		ConstraintValidators.checkIfParameterIsNull(uuid, "id");
		return testRepository.findOneByUuid(uuid);
	}
	
	public void deleteByPk(Long id) {
		logger.info("Deleting Test");		
		Test toDelete = findByPk(id);
		String message = String.format(ID_COULD_NOT_BE_FOUND_MSG, id);
		ConstraintValidators.checkNull(toDelete, message);
		testRepository.deleteById(id);
	}
	
	public void deleteByUUID(UUID uuid) {
		logger.info("Deleting Test");		
		Test toDelete = findByUuid(uuid);
		String message = String.format(ID_COULD_NOT_BE_FOUND_MSG, uuid);
		ConstraintValidators.checkNull(toDelete, message);
		testRepository.deleteById(toDelete.getId());
	}


}
