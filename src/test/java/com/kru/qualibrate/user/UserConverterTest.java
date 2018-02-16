/**
 * 
 */
package com.kru.qualibrate.user;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 * This test builds a dummy USER object, convert to UserRecord (Entity) and add some more details
 * then tested against UserConverter's outcome
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=UserConverter.class)
public class UserConverterTest {
	
	@Autowired
	private UserConverter userConverter;
	
	private final static String FIRST_NAME = "First-Name-01";
	private final static String LAST_NAME = "Last-Name-01";
	private final static String EMAIL = "test-1@email.com";
	private final static String UID = "test-oauth-uid-here";

	@Test
	public void verifyEntityToDTO() {
		User u = new User.UserBuilder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.email(EMAIL)
                .build();
		UserRecord ur = new UserRecord(u);
		Date timestamp = new Date();
		ur.setActivatedAt(timestamp);
		ur.setActive(true);
		ur.setUserId(10L);
		ur.setProvider("dummy-provider");
		ur.setLoginAt(timestamp);
		ur.setLogoutAt(timestamp);
		UserDTO dto = userConverter.convert(ur);

		assertNotNull(dto);
		assertEquals(ur.getFirstName(), dto.getFirstName());
		assertEquals(ur.getLastName(), dto.getLastName());
		assertEquals(ur.getEmail(), dto.getEmail());
		assertEquals(timestamp, dto.getActivatedAt());
		assertEquals(timestamp, dto.getLoginAt());
		assertEquals(timestamp, dto.getLogoutAt());

		assertEquals("dummy-provider", dto.getProvider());
		assertEquals(new Long(10), dto.getUserId());
		assertTrue(dto.isActive());
	}
}
