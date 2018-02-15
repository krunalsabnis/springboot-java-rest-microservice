/**
 * 
 */
package com.kru.qualibrate.project;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kru.qualibrate.ControllerTest;
import com.kru.qualibrate.RestResponsePage;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * REST Controller Test Cases for Project Entity
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTest extends ControllerTest {
	
	@Autowired
	ProjectService projectService;
	
	HttpHeaders headers;
	HttpEntity<String> entity;
	ParameterizedTypeReference<RestResponsePage<ProjectDTO>> responseType;
	
	@Before
	public void setUp() {
		responseType = new ParameterizedTypeReference<RestResponsePage<ProjectDTO>>(){};
    	headers = new HttpHeaders();
    	entity = new HttpEntity<String>(null, headers);
	}
    
    /**
     * Test POST and GET for project
     * posts 10 parallel requests to run test suit quicker
     */
    @Test
    public void bTestPOSTforPorject() {
    	int requestNumbers = 10;
    	IntStream.range(0, requestNumbers).parallel().forEach(x -> {
    		Project p = new Project.ProjectBuilder()
                    .active(false)
                    .code("PROJ-CODE-" + x)
                    .description("PROJ-Description-" + x)
                    .name("PROJ-NAME-" + x)
                    .build();
    		HttpEntity<Project> entity = new HttpEntity<Project>(p, headers);
        	ResponseEntity<ProjectDTO> response = restTemplate.exchange(createURLWithPort("/api/v1/project"),
        			HttpMethod.POST, entity, ProjectDTO.class);
            assertNotNull(response);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
    	});
    }
    
    /**
     * Makes GET call to verify entities created in above test
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    
    @Test
    public void cTestGETforProject() throws JsonParseException, JsonMappingException, IOException {
     	ResponseEntity<RestResponsePage<ProjectDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/project?page=0&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<ProjectDTO>>(){};

    	List<ProjectDTO> projects = response.getBody().getContent();

        assertNotNull(projects);
        assertEquals(10, projects.size());
        assertNotNull(projects.get(0).getCode());
    }
    
    /**
     * Verify there is no more entities to return in page 1, size 10 
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Test
    public void dTestGETforProject() throws JsonParseException, JsonMappingException, IOException {
     	ResponseEntity<RestResponsePage<ProjectDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/project?page=1&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<ProjectDTO>>(){};

    	List<ProjectDTO> projects = response.getBody().getContent();

        assertNotNull(projects);
        assertEquals(0, projects.size());
    }
    
    /**
     * verify 400 (Bad Request) Response when Constraint violated
     * 
     */
	@Test
    public void eTestConstraintViolation() {
		Project p = new Project.ProjectBuilder()
                .active(false)
                .code("PROJ-CODE-100")
                .description("PROJ-Description-100")
                .name("PROJ-NAME-100")
                .build();
		HttpEntity<Project> entity = new HttpEntity<Project>(p, headers);
        ResponseEntity<ProjectDTO> response = restTemplate.exchange(createURLWithPort("/api/v1/project"),
        		HttpMethod.POST, entity, ProjectDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // duplicate save attempt
        response = restTemplate.exchange(createURLWithPort("/api/v1/project"), HttpMethod.POST, entity, ProjectDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
	
	/**
	 * verify invalid input causes 400 (Bad Request)
	 * missing "project name" in request
	 *//*
	@Test
	public void fTestForInvalidInput() {
		Project p = new Project.ProjectBuilder()
                .active(false)
                .code("PROJ-CODE-100")
                .description("PROJ-Description-100")
                .build();
		HttpEntity<Project> entity = new HttpEntity<Project>(p, headers);
        ResponseEntity<ProjectDTO> response = restTemplate.exchange(createURLWithPort("/api/v1/project"),
        		HttpMethod.POST, entity, ProjectDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}*/
}
