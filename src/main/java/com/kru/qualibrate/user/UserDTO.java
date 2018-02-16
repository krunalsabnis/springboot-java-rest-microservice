/**
 * 
 */
package com.kru.qualibrate.user;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kru.qualibrate.project.ProjectRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value=Include.NON_NULL)
public class UserDTO extends User {

	protected Long userId;

	private boolean active;

	private String uid;

	private String provider;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date activatedAt;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date loginAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date logoutAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date timestamp;
	
	@JsonIgnore
	private Set<ProjectRecord> projects;

}
