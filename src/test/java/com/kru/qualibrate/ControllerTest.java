/**
 * 
 */
package com.kru.qualibrate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
@SpringBootTest(classes = QualibrateJavaApiApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest extends QualibrateJavaApiApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	protected TestRestTemplate restTemplate;

	protected String  createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	protected ResponseEntity get(String uri, HttpEntity<?> entity, Class<?> clazz) {
		return restTemplate
			.exchange(createURLWithPort(uri), HttpMethod.GET, entity, clazz);
	}

	protected ResponseEntity post(String uri, HttpEntity<?> entity, Class<?> clazz) {
		return restTemplate.exchange(createURLWithPort(uri),
        		HttpMethod.POST, entity, clazz);
	}

	protected ResponseEntity delete(String uri, HttpEntity<?> entity, Class<?> clazz) {
		return restTemplate.exchange(createURLWithPort(uri),
        		HttpMethod.DELETE, entity, clazz);
	}
	
	protected ResponseEntity put(String uri, HttpEntity<?> entity, Class<?> clazz) {
		return restTemplate.exchange(createURLWithPort(uri),
        		HttpMethod.PUT, entity, clazz);
	}
}
