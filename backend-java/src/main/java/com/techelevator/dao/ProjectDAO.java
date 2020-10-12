package com.techelevator.dao;

import java.security.Principal;
import java.util.List;

import com.techelevator.model.Project;
import com.techelevator.model.Supply;

public interface ProjectDAO {
	
	Project findByProjectId(int projectId);
	
	List<Project> getAllProjects();
	
	List<Project> getProjectsForPercentOwned(Principal principal, int percentOwned);
	
	int addNewProject(Project project);
	
	void updateProject(Project project);
	
	List<Supply> getMissingSuppliesByProjectId(int projectId, Principal principal);

	void deleteProjectFromDatabase(int projectId);
}
