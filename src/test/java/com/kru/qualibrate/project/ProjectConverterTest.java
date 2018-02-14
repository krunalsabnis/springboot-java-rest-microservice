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
		ProjectRecord p = Util.buildProjectRecord();
		Project dto = projectConverter.convert(p);
		assertNotNull(dto);
		assertEquals(dto.getCode(), p.getCode());
		assertEquals(dto.getDescription(), p.getDescription());
		assertEquals(dto.getIcon(), p.getIcon());
		assertEquals(dto.getId(), p.getId());
		assertEquals(dto.getCode(), p.getCode());
		assertEquals(dto.getName(), p.getName());
		assertFalse(dto.isActive());
	}
}
