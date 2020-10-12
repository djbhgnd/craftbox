package com.techelevator.dao;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Project;
import com.techelevator.model.Supply;


@Service
public class ProjectSqlDAO implements ProjectDAO{
	
	 private JdbcTemplate jdbcTemplate;
	 private UserDAO userDAO;
	 private SupplyDAO supplyDAO;
	 
	 public ProjectSqlDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO, SupplyDAO supplyDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.userDAO = userDAO;
		this.supplyDAO = supplyDAO;
	}

	@Override
	public Project findByProjectId(int projectId) {
		Project project = new Project();
	    String sql = "SELECT * "
	    		+    "FROM projects "
	    		+ 	"WHERE project_id = ?";
	    SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
	    while(results.next()) {
	    	project = mapRowToProject(results);
	    	
	    }
	    	return project;
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<Project>();
	    String sql = "SELECT * "
	    		+    "FROM projects "
	    		+ 	 "ORDER BY project_name ASC";
	    SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
	    while(results.next()) {
	    	Project project = mapRowToProject(results);
	    	projects.add(project);
	    	
	    }
	    	return projects;
	}
	
	
	@Override
	public List<Project> getProjectsForPercentOwned(Principal principal, int percentOwned) {
		List<Project> doableProjects = new ArrayList<Project>();
		String sql = "select projects.* " + 
				"from total_supplies as ts " + 
				"join owned_supplies as os on os.project_id = ts.project_id " + 
				"join projects on projects.project_id = ts.project_id " + 
				"where round(((supplies_owned::numeric / supply_count) * 100),2) >= ? " + 
				"and user_id = ?";
		if (percentOwned < 100) {
			sql += "and round(((supplies_owned::numeric / supply_count) * 100),2) < 100";
		}
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, percentOwned, userDAO.findByUsername(principal.getName()).getId());
		  while(results.next()) {
			  Project project = mapRowToProject(results);
			  doableProjects.add(project);
		  }
		return doableProjects;
	}
	
	@Override
	public int addNewProject(Project project) {
		int projectId = 0;
		String sql = "INSERT INTO projects(project_name,project_img,project_instructions,age_range,season) VALUES (?,?,?,?,?) RETURNING project_id";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,project.getProjectName(),project.getProjectImg(),project.getProjectInstructions(),project.getAgeRange(),project.getSeason());
		if(results.next()) {
			projectId = results.getInt("project_id");
		  }
		return projectId;
	}
	
	@Override
	public void updateProject(Project project) {
		String sql = "UPDATE projects SET project_name = ?, project_img = ?, project_instructions = ?, age_range = ?, season = ? WHERE project_id = ?;";
		jdbcTemplate.update(sql,project.getProjectName(),project.getProjectImg(),project.getProjectInstructions(),project.getAgeRange(),project.getSeason(), project.getProjectId());
		
	}
	
	@Override
	public void deleteProjectFromDatabase(int projectId) {
		String sql1 = "DELETE FROM projects_supplies WHERE project_id = ?";
		jdbcTemplate.update(sql1,projectId);
		String sql2 = "DELETE FROM projects WHERE project_id = ?";
		jdbcTemplate.update(sql2,projectId);
	}
	
	@Override
	 public List<Supply> getMissingSuppliesByProjectId(int projectId, Principal principal) {
		 List<Supply> missingSupplies = new ArrayList<Supply>();
		 String sql = "select * " + 
		 		"from supplies " + 
		 		"where supply_id in ( " + 
		 		"    select supply_id " + 
		 		"    from projects_supplies " + 
		 		"    where project_id = ? " + 
		 		"    and supply_id not in ( " + 
		 		"        select ps.supply_id " + 
		 		"        from supplies as s " + 
		 		"        join users_supplies us on us.supply_id = s.supply_id " + 
		 		"        join projects_supplies ps on ps.supply_id = s.supply_id " + 
		 		"        where user_id = ? " + 
		 		"    ));";
		 SqlRowSet results = jdbcTemplate.queryForRowSet(sql,projectId, userDAO.findByUsername(principal.getName()).getId());
		 while(results.next()) {
			 Supply supply = mapRowToSupply(results);
			 missingSupplies.add(supply);
		 }
		 return missingSupplies;
	 }

	
	
	
	
	
	private Project mapRowToProject(SqlRowSet rs) {
        Project project = new Project();
        project.setProjectId((rs.getInt("project_id")));
        project.setProjectName((rs.getString("project_name")));
        project.setProjectImg(rs.getString("project_img"));
        project.setProjectInstructions(rs.getString("project_instructions"));
        project.setAgeRange(rs.getString("age_range"));
        project.setSeason(rs.getString("season"));
        project.setListOfSupplies(supplyDAO.getSuppliesByProjectId(project.getProjectId()));
        return project;
    }
	
	private Supply mapRowToSupply(SqlRowSet rs) {
        Supply supply = new Supply();
        supply.setSupplyId((rs.getInt("supply_id")));
        supply.setSupplyName((rs.getString("supply_name")));
        return supply;
   }	

}
