
package com.kru.qualibrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;

import com.google.common.base.Charsets;


/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
@SpringBootTest(classes = QualibrateJavaApiApplication.class, webEnvironment = SpringBootTest
    .WebEnvironment.DEFINED_PORT)
public class ControllerTest extends QualibrateJavaApiApplicationTests {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected HttpHeaders getHeaders() {
        HttpHeaders headers;
        String basicAuth = new String(Base64.encode("admin:admin@2018".getBytes(Charsets.UTF_8)),
              Charsets.UTF_8);
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + basicAuth);
        headers.add("Accept", "application/json");
        headers.add("Accept", "text/plain");
        return headers;
    }

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
