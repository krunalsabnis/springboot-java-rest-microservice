/**
 * 
 */
package com.kru.qualibrate.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * Controller for Project Entity
 */

@RestController
@RequestMapping("/api/v1/")
@Api(description = "Project lifecycle and test asset administration", tags = "projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@ApiOperation(consumes = "application/json", value = "Get List of Projects", notes = "Get Projects")
	@RequestMapping(method = RequestMethod.GET, value = "/project")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<Project>> getProject(@PageableDefault(value = 50) Pageable pageable) {
		return ResponseEntity.ok(projectService.getProject(pageable));
	}

	@ApiOperation(consumes = "application/json", value = "Create a Project", notes = "Create Project")
	@RequestMapping(method = RequestMethod.POST, value = "/project")
	@ResponseStatus(HttpStatus.CREATED)
	public Project createProject(@RequestBody Project project) {
		return projectService.createProject(project);
	}
}
