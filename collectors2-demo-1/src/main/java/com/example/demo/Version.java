package com.example.demo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Version {
	
	private String id;
	private String major;
	private String minor;
	private Date creationDate;
	private Date editDate;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonIgnore
	public Integer getIntMajor(){
		return Integer.valueOf(major);
	};
	
	@JsonIgnore
	public Integer getIntMinor(){
		return Integer.valueOf(minor);
	};
}
