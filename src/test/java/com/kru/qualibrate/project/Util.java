/**
 * 
 */
package com.kru.qualibrate.project;

import java.util.Date;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
public class Util {

	public static ProjectRecord buildProjectRecord() {
		ProjectRecord p = new ProjectRecord();
		p.setActive(false);
		p.setCode("1234");
		p.setDescription("Project-1234");
		p.setIcon("icon-1234");
		p.setModifiedAt(new Date());
		p.setName("name-1234");
		return p;
	}
	
	public static Project buildProjectDto() {
		Project p = new Project();
		p.setActive(false);
		p.setDescription("desc-1");
		p.setName("name-1");
		p.setCode("code-1");
		p.setIcon("icone-1");
		return p;
	}
}
