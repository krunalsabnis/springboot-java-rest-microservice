/**
 * 
 */
package com.kru.qualibrate.project;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ProjectConverter.class)
public class ProjectConverterTest {
	
	@Autowired
	private ProjectConverter projectConverter;
	
	@Test
	public void verifyEntityToDTO() {
		Project p = new Project.ProjectBuilder()
                .active(false)
                .code("PROJ-CODE-1")
                .description("PROJ-Description-1")
                .name("PROJ-NAME-1")
                .build();
		Date timestamp = new Date();
		ProjectRecord pr = new ProjectRecord(p);
		pr.setId(100L);
		pr.setActive(true);
		pr.setTimestamp(timestamp);

		ProjectDTO dto = projectConverter.convert(pr);
		
		assertNotNull(dto);
		assertEquals(dto.getCode(), pr.getCode());
		assertEquals(dto.getDescription(), pr.getDescription());
		assertEquals(dto.getId(), pr.getId());
		assertEquals(dto.getCode(), pr.getCode());
		assertEquals(dto.getName(), pr.getName());
		assertEquals(dto.getId(), pr.getId());
		assertEquals(dto.getTimestamp(), pr.getTimestamp());
		assertTrue(dto.isActive());
	}
}
