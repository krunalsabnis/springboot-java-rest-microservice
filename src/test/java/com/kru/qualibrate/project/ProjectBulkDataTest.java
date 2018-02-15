/**
 * 
 */
package com.kru.qualibrate.project;


import org.databene.feed4junit.Feeder;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.kru.qualibrate.QualibrateJavaApiApplicationTests;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@RunWith(Feeder.class)
@SuiteClasses({SpringRunner.class})
public class ProjectBulkDataTest extends QualibrateJavaApiApplicationTests {

	@Autowired
	private ProjectService projectService;

	/*
	@Source("db/changelog/data/project-data-v1.csv")
    public void testLogin(String name, String description,
    		String code, String icon, boolean active, Long user_id) {
		projectService.createProject(new Project(name, description,
	    		code, icon, active, user_id));
		//name,description,code,icon,active,user_id
		Page<Project> result = projectService.getProject(new PageRequest(0, 10));
		assertEquals(10, result.getContent().size());
		
		result = projectService.getProject(new PageRequest(1, 50));
		assertEquals(0, result.getContent().size());
		
		result = projectService.getProject(new PageRequest(0, 50));
		assertEquals(25, result.getContent().size());
    }
*/
}
