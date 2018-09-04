package com.example.demo;

import java.util.Comparator;
import java.util.List;

public class ProjectResponseAggregated {

	private String name;
	private List<Version> versions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public Version getLatestVersion() {
		return versions.stream().max(Comparator.comparing(Version::getCreationDate)).orElse(null);
	}

}
