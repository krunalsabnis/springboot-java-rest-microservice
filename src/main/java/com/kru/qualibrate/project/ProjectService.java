/**
 * 
 */
package com.kru.qualibrate.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * Service Layer for Project
 * 
 */
@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ProjectConverter projectConverter;
	
	public Page<Project> getProject(Pageable pageable) {
		//return projectRepo.findAll(pageable).map(ProjectRecord::toProjectDTO);
		return projectRepo.findAll(pageable).map(projectConverter);
	}

	public Project createProject(Project project) {
		return projectConverter.convert(projectRepo.save(new ProjectRecord(project)));
	}

}
