package com.adamfgcross.dentry.core;

import java.util.List;

import com.adamfgcross.dentry.model.DataEntity;

public interface DentryAppCore {
	public List<DataEntity> searchEntities(String searchString);
	public Long addEntity(DataEntity entity);
	public DataEntity getDataEntityByAnchor(String anchor) throws NonuniqueAnchorException;
	public Boolean setNewData(DataEntity entity, String data);
}
