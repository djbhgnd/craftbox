package com.techelevator.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.SupplyDAO;
import com.techelevator.dao.SupplySqlDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Supply;


@RequestMapping("/supply")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RestController
public class SupplyController {

	private SupplyDAO supplyDAO;
	private UserDAO userDAO;
	
	public SupplyController(SupplySqlDAO supplySqlDAO, UserDAO userDAO) {
		this.supplyDAO = supplySqlDAO;
		this.userDAO = userDAO;
	}
	
	@RequestMapping(path = "/allSupplies", method = RequestMethod.GET)
    public List<Supply> getMasterList() {
        List<Supply> masterList = new ArrayList<>();
        masterList = supplyDAO.getMasterListOfSupplies();
        return masterList;
    }
	
	@RequestMapping(path = "/userSupplies", method = RequestMethod.GET)
    public List<Supply> getUserSupplies(Principal principal) {
        List<Supply> userSupplyList = new ArrayList<>();
        String userName = principal.getName();
        userSupplyList = supplyDAO.getUsersSupplies(userName);
        return userSupplyList;
    }
	
    @RequestMapping(path = "/supplyExists", method = RequestMethod.POST)
    public boolean checkIfSupplyExists(@RequestBody String supplyName){
    	boolean supplyExists = supplyDAO.doesSupplyExist(supplyName);
        return supplyExists;
    }
    
    //should return the craftboxId as an int
    @RequestMapping(path = "/supplyToUsersSupplies", method = RequestMethod.POST)
    public int addSupplyToUsersSupplies(@RequestBody Supply supply, Principal principal){
    	String userName = principal.getName();
    	long userId = userDAO.findByUsername(userName).getId();
    	int craftboxId = supplyDAO.addSupplyToUsersSupplies(supply, userId);
    	return craftboxId;
    }
    
    @RequestMapping(path = "/supplyDelete", method = RequestMethod.POST)
    public void deleteSupplyFromUsersSupplies(@RequestBody Supply supply){
    	int craftboxId = supply.getCraftboxId();
    	supplyDAO.removeSupplyFromUsersSupplies(craftboxId);
    }
	
     @RequestMapping(path = "/supplyToUsersSupplies/Update", method = RequestMethod.POST)
     public void updateSupply(@RequestBody Supply supply){
         supplyDAO.updateSupply(supply);
     }
     
     @RequestMapping(path = "/addSupplyToSupplyBank", method = RequestMethod.POST)
     public Supply addSupplyToSupplyTable(@RequestBody Supply supply){
    	 if(!supplyDAO.doesSupplyExist(supply.getSupplyName())) {
             return supplyDAO.addSupplyToSupplyTable(supply);
    	 }else {
    		 return null;
    	 }  	 
     }
     
     @RequestMapping(path = "/addSupplyToSupplyBank/AltName", method = RequestMethod.POST)
     public void addAltNameToAltNameTable(@RequestBody Supply supply){
    	 Supply tempSupply = new Supply();
    	 tempSupply = supplyDAO.getSupplyByName(supply.getSupplyName());
    	 System.out.println("Temp Supply's supply ID = " + tempSupply.getSupplyId());
    	 supply.setSupplyId(tempSupply.getSupplyId());
    	 System.out.println("Supply's supply ID = " + supply.getSupplyId());
    	 supplyDAO.addAltSupplyName(supply);
     }
     
     @RequestMapping(path = "/addSupplyToProject/{projectId}", method = RequestMethod.POST)
     public void addSupplyToProject(@RequestBody Supply supply, @PathVariable int projectId){
    	 supplyDAO.addSupplyToProject(supply, projectId);
     }  
     
     @RequestMapping(path = "/projectSupply/{projectId}", method = RequestMethod.PUT)
     public void editProjectSupplies(@RequestBody Supply[] supply, @PathVariable int projectId){
    	 supplyDAO.editProjectSupplies(supply, projectId);
     }  
     
}
