/**
 * 
 */
package com.kru.qualibrate.project;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Component
public class ProjectConverter implements Converter<ProjectRecord, Project> {


	@Override
	public Project convert(ProjectRecord source) {
		Project p = new Project();
		p.setId(source.getId());
		p.setName(source.getName());
		p.setCode(source.getCode());
		p.setDescription(source.getDescription());
		p.setIcon(source.getIcon());
		p.setModifiedAt(source.getModifiedAt());
		p.setActive(source.isActive());
		return p;
	}

}
