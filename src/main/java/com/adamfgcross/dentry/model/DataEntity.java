package com.adamfgcross.dentry.model;

import jakarta.persistence.*;

@Entity
public class DataEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	private String anchor;
	private String newData;
	
	public DataEntity() {
	}
	
	public DataEntity(String anchor) {
		this.anchor = anchor;
	}
	
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getNewData() {
		return newData;
	}
	public void setNewData(String newData) {
		this.newData = newData;
	}
}
