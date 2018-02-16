/**
 * 
 */
package com.kru.qualibrate.user;

import static org.junit.Assert.*;

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

import com.kru.qualibrate.ControllerTest;
import com.kru.qualibrate.RestResponsePage;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * REST Controller Test Cases for User Entity
 * sequence of test case is important hence MethodSorters.NAME_ASCENDING
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends ControllerTest {
	
	@Autowired
	UserService userService;
	
	HttpHeaders headers;
	HttpEntity<String> entity;
	ParameterizedTypeReference<RestResponsePage<UserDTO>> responseType;
	
	@Before
	public void setUp() {
		responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};
    	headers = new HttpHeaders();
    	entity = new HttpEntity<String>(null, headers);
	}
    
    /**
     * Test POST with valid Request. Then do  GET for USER
     * posts 10 parallel requests to run it quicker
     */
    @Test
    public void aVerifyUserPost() {
    	int requestNumbers = 10;
    	IntStream.range(65, requestNumbers).parallel().forEach(x -> {
    		User u = new User.UserBuilder()
    				.firstName("FirstName" + (char)x)
    				.lastName("LastName" + (char)x)
    				.email("email-" + (char)x + "@email.com")
                    .build();
    		HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        	ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/api/v1/user"),
        			HttpMethod.POST, entity, User.class);
            assertNotNull(response);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
    	});
    }
    
    /**
     * Makes GET call to verify entities created in above test
     * 
     */
    
    @Test
    public void bVerifyUserGet() {
     	ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/user?page=0&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};

    	List<UserDTO> users = response.getBody().getContent();

        assertNotNull(users);
        assertEquals(10, users.size());

        users.stream().parallel().forEach(x -> assertNotNull(x.getEmail()));
        users.stream().parallel().forEach(x -> assertNotNull(x.getFirstName()));
    }
    
    /**
     * Verify there is no more entities to return in page 1, size 10 
     * 
     */
    @Test
    public void cVerifyUserGetForEmptyResponse() {
     	ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/user?page=1&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};

    	List<UserDTO> users = response.getBody().getContent();

        assertNotNull(users);
        assertEquals(0, users.size());
    }
    
    /**
     * verify 400 (Bad Request) Response when Constraint violated
     * 
     */
	@Test
    public void dVerifyConstraintViolates() {
		User u = new User.UserBuilder()
				.firstName("fist_name")
				.lastName("last_name")
				.email("email@test.com")
                .build();
		HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/api/v1/user"),
        		HttpMethod.POST, entity, UserDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        // duplicate save attempt
        response = restTemplate.exchange(createURLWithPort("/api/v1/user"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
	
	/**
	 * Get list of users and delete first one
	 * verify again by making get call
	 * 
	 */
	@Test
    public void eVerifyUserDeleteOnExistingUser() {
		ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/user?page=0&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};
    	Long userId = response.getBody().getContent().get(0).getUserId();

        // delete random user with id = userId
        ResponseEntity<UserDTO> result = restTemplate.exchange(createURLWithPort("/api/v1/user/" + userId),
        		HttpMethod.DELETE, entity, UserDTO.class);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
       
        // verify user with id = userId deleted
        result = restTemplate.exchange(createURLWithPort("/api/v1/user/" + userId),
        		HttpMethod.GET, entity, UserDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	/**
	 * Verify UPDATE for User
	 * Get list of users pick one and Update.
	 *
	 */
	@Test
	public void fVerifyUserUpdate() {
		ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/user?page=0&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};
    	UserDTO user = response.getBody().getContent().get(0);
		String currentFirstName = user.getFirstName();
		String currentLastName = user.getLastName();
		
		//update user's name
		user.setFirstName(currentFirstName + "-done");
		user.setLastName(currentLastName + "-done");
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity<UserDTO> result = restTemplate.exchange(createURLWithPort("/api/v1/user/" + user.getUserId()),
        		HttpMethod.PUT, entity, UserDTO.class);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        
        assertEquals(currentFirstName + "-done", result.getBody().getFirstName());
        assertEquals(currentLastName + "-done", result.getBody().getLastName());
	}

	/**
	 * verifies that User Update request
	 */
	@Test
	public void gVerifyUserUpdateWithBadRequest() {
		ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
     			.exchange(createURLWithPort("/api/v1/user?page=0&size=10"),
    			HttpMethod.GET, entity, responseType);
    	responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};
    	UserDTO user = response.getBody().getContent().get(0);
		String currentFirstName = user.getFirstName();
		
		//update user's name
		user.setFirstName(currentFirstName + "-done");
		
		// but also change user's id. which is not allowed. This should fail the request at controller 
		// only without attempting on DB
		Long validUserId = user.getUserId();
		
		// now user has invalid userId which doesn't match with path variable
		user.setUserId(validUserId + 1);
		
		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(user, headers);

        ResponseEntity<UserDTO> result = restTemplate.exchange(createURLWithPort("/api/v1/user/" + validUserId),
        		HttpMethod.PUT, entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

	/**
	 * verify POST & PUT with bad request bodies which
	 * fails the validation criteria
	 */

	@Test
	public void hVerifyUserRequestValidations() {
		User u = new User.UserBuilder()
				.firstName("fist_name")
				.lastName("last_name")
				.email("bad-email-address-is-here")
                .build();
		HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/api/v1/user"),
        		HttpMethod.POST, entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // also test for PUT
		response = restTemplate.exchange(createURLWithPort("/api/v1/user"),
        		HttpMethod.PUT, entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}
