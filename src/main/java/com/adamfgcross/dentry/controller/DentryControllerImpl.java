package com.adamfgcross.dentry.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.adamfgcross.dentry.core.*;
import com.adamfgcross.dentry.model.DataEntity;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;

@Controller
public class DentryControllerImpl implements DentryController {
	
	private DentryAppCore dentryAppCore;
	private static final Logger logger = LoggerFactory.getLogger(DentryControllerImpl.class);
	
	public DentryControllerImpl(DentryAppCore dentryAppCore) {
		this.dentryAppCore = dentryAppCore;
	}

	public String createDataFromAnchors(String anchorFilePathAddress) throws AnchorFileIOException {
		Path anchorFilePath = Paths.get(anchorFilePathAddress);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(anchorFilePath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("an error occured while reading from anchor file", e);
			throw new AnchorFileIOException();
		}
		if (lines != null) {
			lines.forEach(line -> {
				DataEntity entity = new DataEntity(line);
				dentryAppCore.addEntity(entity);
				logger.debug("added entity: " + entity.getAnchor());
			});
			return "success";
		} else {
			logger.error("lines read from anchor file was null");
		}
		return null;
	}
	
	@Override
	public Boolean attachData(String anchor, String data) {
		DataEntity entity = null;
		try {
			entity = dentryAppCore.getDataEntityByAnchor(anchor);
		}
		catch (NonuniqueAnchorException e) {
			logger.error("NonuniqueAnchorException", e);
			System.out.println("An unexpected error occured while searching for " + anchor);
			return false;
		}
		if (entity != null) {
			dentryAppCore.setNewData(entity, data);
			return true;
		} else {
			return false;
		}
	}
	
	public List<String> getDataEntityResults(String input){
		List<DataEntity> entities = dentryAppCore.searchEntities(input);
		return entities.stream()
				.map(entity -> entity.getAnchor())
				.collect(Collectors.toList());
	}
}
