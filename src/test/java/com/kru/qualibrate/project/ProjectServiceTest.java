/**
 * 
 */
package com.kru.qualibrate.project;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kru.qualibrate.QualibrateJavaApiApplicationTests;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
public class ProjectServiceTest extends QualibrateJavaApiApplicationTests {

	@Autowired
	private ProjectService projectService;

	@Test
	public void test() {
		Project p = Util.buildProjectDto();
		Project savedDto = projectService.createProject(p);
		assertNotNull(savedDto);
		assertEquals(savedDto.getCode(), p.getCode());
		assertEquals(savedDto.getIcon(), p.getIcon());
		assertNotNull(savedDto.getModifiedAt());
	}

}
