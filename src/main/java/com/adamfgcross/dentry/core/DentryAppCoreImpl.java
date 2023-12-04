package com.adamfgcross.dentry.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.adamfgcross.dentry.controller.DentryControllerImpl;
import com.adamfgcross.dentry.model.*;

@Component
public class DentryAppCoreImpl implements DentryAppCore {
	private List<DataEntity> entities = new ArrayList<DataEntity>();
	private DataEntityRepository dataEntityRepository;
	private static final Logger logger = LoggerFactory.getLogger(DentryAppCoreImpl.class);
	
	public DentryAppCoreImpl(DataEntityRepository dataEntityRepository) {
		this.dataEntityRepository = dataEntityRepository;
	}

	public Long addEntity(DataEntity entity) {
		entities.add(entity);
		dataEntityRepository.save(entity);
		return entity.getId();
	}
	@Override
	public List<DataEntity> searchEntities(String searchString) {
		List<DataEntity> searchResults = dataEntityRepository.findAll().stream()
				.filter(dat -> dat.getAnchor().startsWith(searchString))
				.collect(Collectors.toList());
		return searchResults;
	}
	
	@Override
	public DataEntity getDataEntityByAnchor(String anchor) throws NonuniqueAnchorException {
		return dataEntityRepository.findAll().stream()
		.filter(dat -> dat.getAnchor().equals(anchor))
		.findFirst()
		.orElseThrow(() -> {
			logger.error("given a nonunique anchor string: " + anchor);
			return new NonuniqueAnchorException();
		});
	}
	
	@Override
	public Boolean setNewData(DataEntity entity, String data) {
		entity.setNewData(data);
		dataEntityRepository.save(entity);
		return true;
	}
}
