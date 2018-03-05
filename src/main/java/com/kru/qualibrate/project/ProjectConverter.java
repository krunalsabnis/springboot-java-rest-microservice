package com.kru.qualibrate.project;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Component
public class ProjectConverter implements Converter<ProjectRecord, ProjectDTO> {


    @Override
    public ProjectDTO convert(ProjectRecord source) {
        ProjectDTO p = new ProjectDTO();
        p.setId(source.getId());
        p.setName(source.getName());
        p.setDescription(source.getDescription());
        p.setCode(source.getCode());
        p.setIcon(source.getIcon());
        p.setActive(source.isActive());
        p.setTimestamp(source.getTimestamp());
        p.setUserId(source.getUserId());
        return p;
    }

}
