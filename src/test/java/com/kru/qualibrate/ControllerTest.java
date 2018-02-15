/**
 * 
 */
package com.kru.qualibrate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
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
}
