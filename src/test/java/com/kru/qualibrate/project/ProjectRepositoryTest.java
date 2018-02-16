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
public class ProjectRepositoryTest extends QualibrateJavaApiApplicationTests {

	@Autowired
	private ProjectRepository projectRepository;
	
	private final static String CODE = "PROJECT-01";
	private final static String DESCRIPTION = "DESCRIPTION-01";
	private final static String NAME = "NAME-01";
	@Test
	public void testSaveAndReadProjectRepository() {
		Project p = new Project.ProjectBuilder()
                .active(false)
                .code(CODE)
                .description(DESCRIPTION)
                .name(NAME)
                .build();
		ProjectRecord newEntity = new ProjectRecord(p);
		newEntity.setUserId(123L);
		ProjectRecord savedEntity = projectRepository.save(newEntity);
		
		assertEquals(newEntity.getCode(), savedEntity.getCode());
		assertEquals(CODE, savedEntity.getCode());
		assertEquals(newEntity.getDescription(), savedEntity.getDescription());
		assertEquals(DESCRIPTION, savedEntity.getDescription());
		assertEquals(new Long(123), savedEntity.getUserId());
		assertNotNull(savedEntity.getTimestamp());

		// verify findOne by Id method for saved entity
		ProjectRecord readEntity = projectRepository.findOne(savedEntity.getId());
		assertNotNull(readEntity);
		
		// Test Update
		readEntity.setActive(true);
		ProjectRecord updated = projectRepository.save(readEntity);
		assertTrue(updated.isActive());
		
		//find for random non-existing record
		assertNull(projectRepository.findOne(2345L));
	}
}
