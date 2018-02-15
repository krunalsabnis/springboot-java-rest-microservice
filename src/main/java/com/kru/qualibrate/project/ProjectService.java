/**
 * 
 */
package com.kru.qualibrate.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kru.qualibrate.exceptions.InvalidRequestException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * Service Layer for Project
 * 
 */
@Service
@Slf4j
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ProjectConverter projectConverter;
	
	public Page<ProjectDTO> getProject(Pageable pageable) {
		return projectRepo.findAll(pageable).map(projectConverter);
	}

	@Transactional
	public ProjectDTO createProject(Project project) {
		ProjectRecord saved;
		try {
			saved = projectRepo.save(new ProjectRecord(project));
		} catch (DataIntegrityViolationException e) {
			log.error("project with code {} already exists", project.getCode());
			throw new InvalidRequestException(e, "error creating new project."
					+ " code must be unique");
		}
		return projectConverter.convert(saved);
	}

}
