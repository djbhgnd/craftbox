package com.techelevator.model;

public class Supply {

	//properties
	private int supplyId;
	private String supplyName;
	private int craftboxId;
	private String color;
	private String notes;
	private String altName;
	private boolean isOptional;
	
	//methods
	public String getAltName() {
		return altName;
	}

	public void setAltName(String altName) {
		this.altName = altName;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getCraftboxId() {
		return craftboxId;
	}

	public void setCraftboxId(int craftboxId) {
		this.craftboxId = craftboxId;
	}
	
	public int getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	
	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	
	//constructors
	public Supply(int supplyId, String supplyName) {
		this.supplyId = supplyId;
		this.supplyName = supplyName;
	}
	
	public Supply() {
		
	}

	

}
