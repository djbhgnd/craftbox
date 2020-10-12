package com.techelevator.model;

import java.util.List;

public class Project {
	
	private int projectId;
	private String projectName;
	private String projectImg;
	private String projectInstructions;
	private String ageRange;
	private String season;
	private List<Supply> listOfSupplies;
	
	public Project() {
		
	}
	
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectImg() {
		return projectImg;
	}
	public void setProjectImg(String projectImg) {
		this.projectImg = projectImg;
	}
	public String getProjectInstructions() {
		return projectInstructions;
	}
	public void setProjectInstructions(String projectInstructions) {
		this.projectInstructions = projectInstructions;
	}
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}

	public List<Supply> getListOfSupplies() {
		return listOfSupplies;
	}

	public void setListOfSupplies(List<Supply> listOfSupplies) {
		this.listOfSupplies = listOfSupplies;
	}
	
	
	
}
