/**
 * 
 */
package com.kru.qualibrate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception handler for Eth service 
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class EtheriumServiceException extends RuntimeException {
	private static final long serialVersionUID = 8270942695559481165L;

	public EtheriumServiceException(String... message) {
		super(String.join(" ", message));
	}
	
	public EtheriumServiceException(Throwable t, String... message) {
		super(String.join(" ", message), t);
	}
}
