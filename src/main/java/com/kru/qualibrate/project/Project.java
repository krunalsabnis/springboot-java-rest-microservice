/**
 * 
 */
package com.kru.qualibrate.project;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

	@ApiModelProperty(name = "id", dataType = "String", required = false,
	    notes = "Unique identifier", example = "1")
	private Long id;

	@NotNull
	@Size(min = 1, max = 20)
	@Pattern(regexp = "^[a-zA-Z] {1,20}")
	@ApiModelProperty(name = "name", dataType = "String", required = true,
	    notes = "Project name", example = "CRM Project")
	private String name;
	
	@Size(min = 1, max = 100)
	@Pattern(regexp = "^[a-zA-Z] {0,100}")
	@ApiModelProperty(name = "description", dataType = "String", required = false,
	    notes = "Details about project content", example = "Automation suite for release 101-B")
	private String description;

	@Size(min = 0, max = 20)
	@Pattern(regexp = "^[a-zA-Z] {0,20}")
	@ApiModelProperty(name = "code", dataType = "String", required = false,
	    notes = "Generic identifier", example = "PRJ-001")
	private String code;


	@ApiModelProperty(name = "active", dataType = "String", required = false,
		    notes = "In archive?", example = "true/false")
	private boolean active;

}
