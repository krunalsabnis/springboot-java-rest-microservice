package com.kru.qualibrate.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kru.qualibrate.project.ProjectDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * Controller for User Entity
 */

@RestController
@RequestMapping("/api/v1/")
@Api(description = "Platform access administration", tags = "users", consumes = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get List of Users", notes = "List all users")
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(userService.getUser(pageable));
    }

    @ApiOperation(value = "Create a User", notes = "Creates a new user")
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @ApiOperation(value = "Get User by Id", notes = "Get User")
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @ApiOperation(value = "Delete a User", notes = "Delete")
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @ApiOperation(value = "Update a User", notes = "Update")
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDto) {
        return userService.updateUser(userId, userDto);
    }

    @ApiOperation(value = "Get projects for User", notes = "List of Projects")
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/project")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProjectDTO>> getUserProject(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProjectFor(userId));
    }

    @ApiOperation(value = "Assign a project to user", notes = "Assign a Projects")
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}/project/{projectId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void assignProject(@PathVariable Long userId,
            @PathVariable Long projectId) {
        userService.assignProject(userId, projectId);
    }
}
