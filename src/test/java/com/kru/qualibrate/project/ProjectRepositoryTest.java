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
	
	@Test
	public void testSaveAndReadProjectRepository() {
		ProjectRecord newEntity = Util.buildProjectRecord();
		
		ProjectRecord savedEntity = projectRepository.save(newEntity);
		assertEquals(savedEntity.getCode(), newEntity.getCode());
		assertEquals(savedEntity.getIcon(), newEntity.getIcon());
		assertNotNull(savedEntity.getTimestamp());

		ProjectRecord readEntity = projectRepository.getOne(savedEntity.getId());
		assertNotNull(readEntity);
		
		assertNull(projectRepository.findOne(2345L));
	}
}
