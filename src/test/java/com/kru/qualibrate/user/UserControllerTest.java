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
@SuppressWarnings("unchecked")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends ControllerTest {

    @Autowired
    UserService userService;

    HttpHeaders headers;
    HttpEntity<String> entity;
    ParameterizedTypeReference<RestResponsePage<UserDTO>> responseType;

    @Before
    public void setUp() {
        headers = getHeaders();
        responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};
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
                    .firstName("FirstName" + (char) x)
                    .lastName("LastName" + (char) x)
                    .email("email-" + (char) x + "@email.com")
                    .build();
            HttpEntity<User> entity = new HttpEntity<User>(u, headers);
            ResponseEntity<User> response = post("/api/v1/user", entity, User.class);
            assertNotNull(response);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        });
    }

    /**
     * Makes GET call to verify entities created in above test
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
     * Verify there is no more entities to return in page 100, size 2500
     * we load 25 records in H2 in test profile
     */
    @Test
    public void cVerifyUserGetForEmptyResponse() {
        ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
                 .exchange(createURLWithPort("/api/v1/user?page=100&size=2500"),
                HttpMethod.GET, entity, responseType);
        responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};

        List<UserDTO> users = response.getBody().getContent();

        assertNotNull(users);
        assertEquals(0, users.size());
    }

    /**
     * verify 400 (Bad Request) Response when Constraint violated
     */
    @Test
    public void dVerifyConstraintViolates() {
        User u = new User.UserBuilder()
                .firstName("Krunal")
                .lastName("Sabnis")
                .email("email@test.com")
                .build();
        HttpEntity<User> entity = new HttpEntity<User>(u, getHeaders());
        ResponseEntity<UserDTO> response = post("/api/v1/user", entity, UserDTO.class);
        /*ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/api/v1/user"),
                HttpMethod.POST, entity, UserDTO.class);*/
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // duplicate save attempt
        response = post("/api/v1/user", entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Get list of users and delete first one
     * verify again by making get call
     */
    @Test
    public void eVerifyUserDeleteOnExistingUser() {
        ResponseEntity<RestResponsePage<UserDTO>> response = restTemplate
                 .exchange(createURLWithPort("/api/v1/user?page=0&size=10"),
                HttpMethod.GET, entity, responseType);
        responseType = new ParameterizedTypeReference<RestResponsePage<UserDTO>>(){};
        Long userId = response.getBody().getContent().get(0).getUserId();

        // delete random user with id = userId
        ResponseEntity<UserDTO> result = delete("/api/v1/user/" + userId, entity, UserDTO.class);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // verify user with id = userId deleted
        result = get("/api/v1/user/" + userId, entity, UserDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    /**
     * Verify UPDATE for User
     * Get list of users pick one and Update.
     * also test UPDATE for Bad Request
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
        user.setFirstName(currentFirstName + "changed");
        user.setLastName(currentLastName + "changed");

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity<UserDTO> result = put("/api/v1/user/" + user.getUserId(),
                entity, UserDTO.class);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());

        assertEquals(currentFirstName + "changed", result.getBody().getFirstName());
        assertEquals(currentLastName + "changed", result.getBody().getLastName());

        // test for a bad request parameter - invalid name
        user.setFirstName("namewith-hyphen");
        entity = new HttpEntity<User>(user, headers);
        result = put("/api/v1/user/" + user.getUserId(), entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
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

        ResponseEntity<UserDTO> result = put("/api/v1/user/" + validUserId, entity, UserDTO.class);
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

        ResponseEntity<UserDTO> response = post("/api/v1/user", entity, UserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
