package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import com.techelevator.model.Supply;

@Service
public class SupplySqlDAO implements SupplyDAO {

	 private JdbcTemplate jdbcTemplate;

	 public SupplySqlDAO(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }
	 
	 @Override
	 public List<Supply> getMasterListOfSupplies() {
		List<Supply> supplies = new ArrayList<Supply>();
	    String sql = "SELECT supply_id, supply_name "
	    		+    "FROM supplies "
	    		+ 	 "ORDER BY supply_name ASC";
	    SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
	    while(results.next()) {
	    	Supply supply = mapRowToSupply(results);
	        supplies.add(supply);
	    }
	    	return supplies;
	 }
	 
	 @Override
	 public boolean doesSupplyExist(String supplyName) {
		 String sql = "SELECT COUNT(supply_name) "
				 	+ "FROM supplies "
				    + "WHERE UPPER(supply_name) = UPPER(?)";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,supplyName);
		 int countOfSupply = 0;
		 if(results.next()) {
			 countOfSupply = results.getInt("COUNT");
		 }
		 boolean doesSupplyExist = false;
		 if(countOfSupply >= 1) {
			 doesSupplyExist = true;
		 }
		 return doesSupplyExist;
		 
	 }
	 
	 @Override
	 public Supply getSupplyById(int supplyId) {
		 String sql = "SELECT supply_id, supply_name " 
		 		     +"FROM supplies "
		 		     +"WHERE supply_id = ?";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,supplyId);
		 Supply supply = new Supply();
		 if(results.next()) {
			 supply = mapRowToSupply(results);
		 }
		 return supply;
	 }
	 
	 @Override
	 public Supply getSupplyByName(String supplyName) {
		 String sql = "SELECT supply_id, supply_name " 
		 		     +"FROM supplies "
		 		     +"WHERE supply_name = ? "
		 		     + "LIMIT 1";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,supplyName);
		 Supply supply = new Supply();
		 if(results.next()) {
			 supply = mapRowToSupply(results);
		 }
		 return supply;
	 }
	 
	 @Override
	 public List<Supply> getUsersSupplies(String userName){
		 List<Supply> supplies = new ArrayList<Supply>();
		 String sql = 	"SELECT users_supplies.craftbox_id, supplies.supply_id, supplies.supply_name, users_supplies.color, users_supplies.notes " + 
		  				"FROM supplies " + 
		  				"INNER JOIN users_supplies " + 
		  				"ON users_supplies.supply_id = supplies.supply_id " + 
		  				"WHERE user_id = ( " + 
		  				"    SELECT user_id " + 
		  				"    FROM users " + 
		  				"    WHERE username = ? " + 
		  				")" +
		  				" ORDER BY supplies.supply_name ASC";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userName);
		 while(results.next()) {
			 Supply supply = mapRowToSupplyWithCraftboxId(results);
			 supplies.add(supply);
		 }
		 return supplies;
	 }
	 
	 @Override
	 public List<Supply> getSuppliesByProjectId(int projectId){
		 List<Supply> supplies = new ArrayList<Supply>();
		 String sql = 	"select projects_supplies.project_id, supplies.supply_name, supplies.supply_id, color, notes " + 
		 		"from projects_supplies " + 
		 		"inner join supplies on supplies.supply_id = projects_supplies.supply_id " + 
		 		"where projects_supplies.project_id = ?;";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,projectId);
		 while(results.next()) {
			 Supply supply = mapRowToProjectSupply(results);
			 supplies.add(supply);
		 }
		 return supplies;
	 }
	 
	 @Override
	 public int addSupplyToUsersSupplies(Supply supply, long userId) {
		 int supplyId = supply.getSupplyId();
		 String notes = supply.getNotes();
		 String color = supply.getColor();
		 String sql = "INSERT INTO users_supplies(supply_id, user_id, notes, color) VALUES (?,?,?,?) RETURNING craftbox_id";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql, supplyId, userId, notes, color);
		 int craftboxId = 0;
		 if(results.next()) {
			 craftboxId = results.getInt("craftbox_id");
		 }
		 return craftboxId;
	 }
	 
	 @Override
	 public void removeSupplyFromUsersSupplies(int craftboxId) {
		 String sql = "DELETE FROM users_supplies WHERE craftbox_id = ?";
		 jdbcTemplate.update(sql,craftboxId);
	 }

	 @Override
	 public void updateSupply(Supply supply){
		 String sql = "UPDATE users_supplies SET notes = ?, color = ? WHERE craftbox_id = ?";
		 jdbcTemplate.update(sql, supply.getNotes(), supply.getColor(), supply.getCraftboxId());
	 }
	 
	 @Override
	 public void addAltSupplyName(Supply supply) {
		 String sql = "INSERT INTO supplies_alternate_names(supply_id, alternate_name) VALUES(?,?)";
		 jdbcTemplate.update(sql,supply.getSupplyId(),supply.getAltName());
	 }
	 
	 @Override
	 public Supply addSupplyToSupplyTable(Supply supply) {
		 String sql = "INSERT INTO supplies(supply_name) VALUES(?) RETURNING supply_id";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,supply.getSupplyName());
		 int supplyId = 0;
		 if(results.next()) {
			 supplyId = results.getInt("supply_id");
		 }
		 supply.setSupplyId(supplyId);
		 return supply;
	 }
	 
	 @Override
		public void addSupplyToProject(Supply supply, int projectId) {
		 String sql = "INSERT INTO projects_supplies(supply_id,project_id,color,notes,optional) VALUES(?,?,?,?,?)";
		 jdbcTemplate.update(sql,supply.getSupplyId(),projectId, supply.getColor(),supply.getNotes(),supply.isOptional());
		} 
	 
	 
	 @Override
		public void editProjectSupplies(Supply[] supply, int projectId) {
		 String sql1 = "DELETE FROM projects_supplies WHERE project_id = ?";
		 jdbcTemplate.update(sql1,projectId);
		 
		 String sql2 = "INSERT INTO projects_supplies(supply_id,project_id,color,notes,optional) VALUES(?,?,?,?,?)";
		 
		 for(int i = 0; i<supply.length; i++) {
		jdbcTemplate.update(sql2,supply[i].getSupplyId(),projectId, supply[i].getColor(),supply[i].getNotes(),supply[i].isOptional());
		 }
		} 
	 
	 
	 
	 
	 
	 private Supply mapRowToSupply(SqlRowSet rs) {
	        Supply supply = new Supply();
	        supply.setSupplyId((rs.getInt("supply_id")));
	        supply.setSupplyName((rs.getString("supply_name")));
	        return supply;
	   }
	 private Supply mapRowToSupplyWithCraftboxId(SqlRowSet rs) {
	        Supply supply = new Supply();
	        supply.setSupplyId(rs.getInt("supply_id"));
	        supply.setSupplyName(rs.getString("supply_name"));
	        supply.setCraftboxId(rs.getInt("craftbox_id"));
	        supply.setColor(rs.getString("color"));
	        supply.setNotes(rs.getString("notes"));
	        return supply;
	   }


	

	 private Supply mapRowToProjectSupply(SqlRowSet rs) {
	        Supply supply = new Supply();
	        supply.setSupplyId((rs.getInt("supply_id")));
	        supply.setSupplyName((rs.getString("supply_name")));
	        supply.setColor(rs.getString("color"));
	        supply.setNotes(rs.getString("notes"));
	        return supply;
	   }
	 

}
