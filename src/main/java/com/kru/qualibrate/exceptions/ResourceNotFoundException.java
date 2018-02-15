/**
 * 
 */
package com.kru.qualibrate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String... message) {
		super(String.join(" ", message));
	}
	
	public ResourceNotFoundException(Throwable t, String... message) {
		super(String.join(" ", message), t);
	}
}
