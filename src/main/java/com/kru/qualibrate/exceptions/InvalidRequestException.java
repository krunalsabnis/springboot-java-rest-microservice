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
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
	public InvalidRequestException(String... message) {
		super(String.join(" ", message));
	}
	
	public InvalidRequestException(Throwable t, String... message) {
		super(String.join(" ", message), t);
	}
}
