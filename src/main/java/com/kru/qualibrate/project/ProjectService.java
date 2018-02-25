/**
 * 
 */
package com.kru.qualibrate.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kru.qualibrate.exceptions.InvalidRequestException;
import com.kru.qualibrate.exceptions.ResourceNotFoundException;

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

	@PreAuthorize("hasRole('ADMIN')")
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

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public ProjectDTO assignToUser(Long userId, Long projectId) {
		ProjectRecord p = findOne(projectId);
		p.setUserId(userId);
		return projectConverter.convert(projectRepo.save(p));
	}

	private ProjectRecord findOne(Long projectId) {
		ProjectRecord p = projectRepo.findOne(projectId);
		if (p == null)
			throw new ResourceNotFoundException("Project id " + projectId + "does not exists.");
		return p;
	}
}
