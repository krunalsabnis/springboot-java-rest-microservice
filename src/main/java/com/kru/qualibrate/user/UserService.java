/**
 * 
 */
package com.kru.qualibrate.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kru.qualibrate.exceptions.InvalidRequestException;
import com.kru.qualibrate.exceptions.ResourceNotFoundException;
import com.kru.qualibrate.project.ProjectConverter;
import com.kru.qualibrate.project.ProjectDTO;
import com.kru.qualibrate.project.ProjectService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * Service Layer for User Entity
 * 
 */
@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private ProjectConverter projectConverter;

	@Autowired
	private com.kru.qualibrate.commons.NonNullCopyBeanUtils<UserRecord, UserDTO> nonNullCopyBeanUtils;

	public Page<UserDTO> getUser(Pageable pageable) {
		return userRepository.findAll(pageable).map(userConverter);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public UserDTO getUser(Long id) {
		UserRecord user = userRepository.findOne(id);
		if (user == null)
			throw new ResourceNotFoundException("User not found");
		return userConverter.convert(user);
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public User createUser(User user) {
		UserRecord saved;
		try {
			saved = userRepository.save(new UserRecord(user));
		} catch (DataIntegrityViolationException e) {
			log.error("user with email {} already exists", user.getEmail());
			throw new InvalidRequestException(e, "error creating new user."
					+ " user with same email already exists");
		}
		return userConverter.convert(saved);
	}


	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void deleteUser(Long id) {
		if (userRepository.exists(id))
			userRepository.delete(id);
		else
			throw new ResourceNotFoundException("User not found");
	}


	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public UserDTO updateUser(Long id, UserDTO userDto) {
		if (userDto.getUserId() != null && userDto.getUserId() != id)
			throw new InvalidRequestException("unexpected userId in request body : " + userDto.getUserId());
		UserRecord userRecord = userRepository.findOne(id);
		if (userRecord == null)
			throw new ResourceNotFoundException("User not found");
		nonNullCopyBeanUtils.copyNonNullProperties(userRecord, userDto);
		System.out.println(userRecord);
		return userConverter.convert(userRepository.save(userRecord));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<ProjectDTO> getProjectFor(Long id) {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		getUser(id).getProjects().forEach(x -> list.add(projectConverter.convert(x)));
		return list;
	}


	@PreAuthorize("hasRole('ADMIN')")
	public ProjectDTO assignProject(Long userId, Long projectId) {
		if (!userRepository.exists(userId))
			throw new ResourceNotFoundException("User not found");
		return projectService.assignToUser(userId, projectId);
	}
}
