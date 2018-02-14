/**
 * 
 */
package com.kru.qualibrate.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
	private Long id;
	
	@NotNull
	private String name;
		
	private String description;

	private String code;

	private String icon;

	private boolean active;

	private Date modifiedAt;

	@Column(name = "user_id")
	private Long userId;


}
