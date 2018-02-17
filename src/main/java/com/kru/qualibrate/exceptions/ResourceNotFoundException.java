/**
 * 
 */
package com.kru.qualibrate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception handler for 404 Not found. 
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8270942695559481165L;

	public ResourceNotFoundException(String... message) {
		super(String.join(" ", message));
	}
	
	public ResourceNotFoundException(Throwable t, String... message) {
		super(String.join(" ", message), t);
	}
}
