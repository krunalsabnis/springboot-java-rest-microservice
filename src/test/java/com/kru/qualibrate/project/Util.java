/**
 * 
 */
package com.kru.qualibrate.project;

import java.util.Date;


import io.bloco.faker.Faker;

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
		p.setTimestamp(new Date());
		p.setName("name-1234");
		return p;
	}

}
