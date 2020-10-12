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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.ProjectDAO;
import com.techelevator.dao.ProjectSqlDAO;
import com.techelevator.model.Project;
import com.techelevator.model.Supply;


@RequestMapping("/projects")
@PreAuthorize("permitAll")
@RestController
@CrossOrigin
public class ProjectController {
	
	private ProjectDAO projectDAO;
	
	public ProjectController(ProjectSqlDAO projectSqlDAO) {
		this.projectDAO = projectSqlDAO;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	 public List<Project> getAllProjects(@RequestParam(defaultValue = "0") int percentOwned, Principal principal) {
        List<Project> projects = new ArrayList<>();
        if(percentOwned > 0 && principal != null) {
        	projects = projectDAO.getProjectsForPercentOwned(principal, percentOwned);
        }
        else {
        	projects = projectDAO.getAllProjects();
        }
        return projects;
    }
	
	@RequestMapping(value= "/{id}", method = RequestMethod.GET)
	public Project getProjectByID(@PathVariable int id) {
		Project project = new Project();
		project = projectDAO.findByProjectId(id);
		return project;
		
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public int addNewProject(@RequestBody Project project) {
	return projectDAO.addNewProject(project);
	}
	
	@RequestMapping(path = "/{id}/update", method = RequestMethod.PUT)
    public void updateProject(@RequestBody Project project, @PathVariable int id){
        projectDAO.updateProject(project);
    }	
	
	@RequestMapping(path = "/{id}/missingSupplies", method = RequestMethod.GET)
    public List<Supply> getMissingSupplies(@PathVariable int id,  Principal principal){
        return projectDAO.getMissingSuppliesByProjectId(id, principal);
    } 
	
	@RequestMapping(value="/{projectId}", method = RequestMethod.DELETE)
	public void deleteProjectFromDatabase(@PathVariable int projectId) {
	projectDAO.deleteProjectFromDatabase(projectId);
	}
	
	
	

}
