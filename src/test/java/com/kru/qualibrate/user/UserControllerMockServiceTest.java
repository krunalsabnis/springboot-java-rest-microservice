package com.kru.qualibrate.user;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.kru.qualibrate.AbstractControllerTest;
import com.kru.qualibrate.QualibrateJavaApiApplicationTests;

public class UserControllerMockServiceTest extends AbstractControllerTest {

    public static final String PCN = "K700";
    public static final String SERIAL_NUMBER = "1234567";
    public static final String BPN = "1234567";
    public static final String PADDED_BPN = "000" + BPN;
    public static final String OKTA_ID = "abcd1234efgh1234abcd";
    public static final String TEMPLATE_ORDER_ID = "123456";
    public static final String CARTRIDGE = "RED";
    public static final String INK_LEVEL = "LOW";

    @MockBean
    private UserService userService;

    @Override
    protected String url() {
        return "/api/v1/user";
    }

    @SuppressWarnings("deprecation")
	@Test
    public void getUsersList() throws Exception {
    	document.snippets(
                responseFields(
                        fieldWithPath("content").description("Array of user records"),
                        fieldWithPath("content[].userId").description("User's unique identifier number"),
                        fieldWithPath("content[].active")
                                .description("Current status of User. It can be Active | Inactive"),
                        fieldWithPath("content[].uid")
                                .description("User's uuid"),
                        fieldWithPath("content[].provider")
                                .description("User's OAuth provider"),
                        fieldWithPath("content[].activatedAt")
                                .description("Timestamp when user has been activated"),
                        fieldWithPath("content[].loginAt")
                                .description("User's last login time"),
                        fieldWithPath("content[].logoutAt")
                                .description("User's last logout time"),
                        fieldWithPath("content[].timestamp")
                                .description("Timestamp when user details were updated last"),
                        fieldWithPath("content[].firstName").description(
                                "First name of the user"),
                        fieldWithPath("content[].lastName")
                                .description("Last name of the user"),
                        fieldWithPath("content[].email")
                                .description("Email id of the user. There can not be more than one user with same email id"),
                        fieldWithPath("totalElements")
                                .description("Total number of elements in page"),
                        fieldWithPath("totalPages")
                                .description("Total number of pages"),
                        fieldWithPath("last")
                                .description("Flag to denote if this is a last page or result"),
                        fieldWithPath("size")
                                .description("Size of page"),
                        fieldWithPath("number")
                                .description("Page number"),
                        fieldWithPath("sort")
                                .description("Details about fields used to sort results"),
                        fieldWithPath("first")
                                .description("Is this the first page"),
                        fieldWithPath("numberOfElements")
                                .description("Total number of elements")));

    	when(userService.getUser(any(PageRequest.class))).thenReturn(getDummyUser());
        List<UserDTO> response = page(UserDTO.class, "?page=0&size=2");

        assertNotNull(response);
    }
    
    private Page<UserDTO> getDummyUser() {
		UserDTO u = new UserDTO(100L, false, "uid-0001", "provider",
				new Date(), new Date(), new Date(), new Date(), null);
		u.setFirstName("John");
		u.setLastName("Smith");
		u.setEmail("john.smith@email.com");
		return new PageImpl<>(Arrays.asList(u));
    }
}
