/**
 * 
 */
package com.kru.qualibrate.user;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kru.qualibrate.QualibrateJavaApiApplicationTests;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * Test Cases for USER repository
 */
public class UserRepositoryTest extends QualibrateJavaApiApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	private final static String FIRST_NAME = "First-Name-01";
	private final static String LAST_NAME = "Last-Name-01";
	private final static String EMAIL = "test-1@email.com";
	private final static String UID = "test-oauth-uid-here";
	
	@Test
	public void testSaveAndReadUserRepository() {
		User u = new User.UserBuilder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.email(EMAIL)
                .build();
		UserRecord newEntity = new UserRecord(u);
		newEntity.setActive(true);
		newEntity.setUid(UID);
		UserRecord savedEntity = userRepository.save(newEntity);
		assertEquals(FIRST_NAME, savedEntity.getFirstName());
		assertEquals(LAST_NAME, savedEntity.getLastName());
		assertEquals(UID, savedEntity.getUid());
		assertEquals(EMAIL, savedEntity.getEmail());
		assertTrue(savedEntity.isActive());
		assertNotNull(savedEntity.getTimestamp());
		assertNotNull(savedEntity.getUserId());
		
		// Now Read entity by Id
		assertNotNull(userRepository.findOne(savedEntity.getUserId()));
		
		// test random non existing entity
		assertNull(userRepository.findOne(10034L));
		
		//test upsert
		savedEntity.setEmail("this-is-new-email");
		UserRecord update = userRepository.save(savedEntity);
		assertEquals("this-is-new-email", update.getEmail());
	}
}
