/**
 * 
 */
package com.kru.qualibrate.project;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Data
public class Project {

	@JsonIgnore
	private Long id;
	
	private String name;
		
	private String description;

	private String code;

	private String icon;

	private boolean active;

	@JsonIgnore
	private Date modifiedAt;

}
