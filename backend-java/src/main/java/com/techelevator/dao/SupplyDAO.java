package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Supply;

public interface SupplyDAO {

	List<Supply> getMasterListOfSupplies();
	
	boolean doesSupplyExist(String supplyName);
	
	Supply getSupplyById(int supplyId);
	
	Supply getSupplyByName(String supplyName);
	
	List<Supply> getUsersSupplies(String userName);
	
	List<Supply> getSuppliesByProjectId(int projectId);

	int addSupplyToUsersSupplies(Supply supply, long userId);
	
	void removeSupplyFromUsersSupplies(int craftboxId);

	void updateSupply(Supply supply);
	
	Supply addSupplyToSupplyTable(Supply supply);
	
	void addAltSupplyName(Supply supply);
	
	void addSupplyToProject(Supply supply, int projectId);
	
	void editProjectSupplies(Supply[] supply ,int projectId);
	
}
