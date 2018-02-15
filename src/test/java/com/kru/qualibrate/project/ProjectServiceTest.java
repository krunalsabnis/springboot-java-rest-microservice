/**
 * 
 */
package com.kru.qualibrate.project;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kru.qualibrate.QualibrateJavaApiApplicationTests;
import com.kru.qualibrate.exceptions.InvalidRequestException;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceTest extends QualibrateJavaApiApplicationTests {

	@Autowired
	private ProjectService projectService;

	private Project p = new Project.ProjectBuilder()
			                       .active(false)
			                       .code("PROJ-CODE-01")
			                       .description("PROJ-01-Description")
			                       .name("PROJ-NAME-01")
			                       .build();

	@Test
	public void aTestSaveMethod() {
		ProjectDTO savedDto = projectService.createProject(p);
		assertNotNull(savedDto);
		assertEquals(savedDto.getCode(), p.getCode());
		assertNotNull(savedDto.getTimestamp());
	}

	@Test(expected = InvalidRequestException.class)
	public void bTestDuplicateSave() {
		projectService.createProject(p);
		projectService.createProject(p);
	}
	
	@Test
	public void cTestGetPaginatedData() {
		Page<ProjectDTO> result = projectService.getProject(new PageRequest(0, 10));
		assertEquals(10, result.getContent().size());

		result = projectService.getProject(new PageRequest(1, 200));
		assertEquals(0, result.getContent().size());

	}
}
