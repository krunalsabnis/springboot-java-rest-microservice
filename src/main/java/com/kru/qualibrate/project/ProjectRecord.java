/**
 * 
 */
package com.kru.qualibrate.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * Project Entity represents Database table "project"
 */
@Data
@NoArgsConstructor
@Entity(name="project")
public class ProjectRecord {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String name;
		
	private String description;

	private String code;

	private String icon;

	private boolean active;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date timestamp;

	@Column(name="user_id")
	private Long userId;

	ProjectRecord(Project project) {
		this.name = project.getName();
		this.description = project.getDescription();
		this.timestamp = new Date();
		this.active = project.isActive();
		this.code = project.getCode();
		this.id = project.getId();
	}
}
