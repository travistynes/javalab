package com.mes.payouts.security.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The @ActiveProfile specifies the properties file that will be loaded.
 * When a profile is not set, it will default to the application.properties
 * and try to make connections to the external database.
 *
 * Classpath resources and files will be loaded from /src/main/resources, but will
 * be overridden by those of the same name if they exist in /src/test/resources.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"unittest"})
@SpringBootTest
public class UserTest {
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);

	@Autowired
	private MesUserDetailsService userDetailsService;
	
	// Runs before each test method.
	@Before
	public void before() throws Exception {

	}

	// Runs after each test method.
	@After
	public void after() throws Exception {

	}

	@Test
	public void joinUserDetails() throws Exception {
		MesUserDetails user = (MesUserDetails)userDetailsService.loadUserByUsername("user");
		MesUserDetails bob = (MesUserDetails)userDetailsService.loadUserByUsername("bob");

		Assert.assertEquals("user@example.com", user.getEmail());
		Assert.assertEquals("bob@example.com", bob.getEmail());
		Assert.assertTrue(user.isEnabled());
		Assert.assertFalse(bob.isEnabled());
	}

	@Test
	public void joinNoUserDetails() throws Exception {
		MesUserDetails user = (MesUserDetails)userDetailsService.loadUserByUsername("ralph");

		Assert.assertNull(user.getEmail());
	}

	@Test
	public void joinDepartment() throws Exception {
		MesUserDetails user = (MesUserDetails)userDetailsService.loadUserByUsername("user");
		MesUserDetails bob = (MesUserDetails)userDetailsService.loadUserByUsername("bob");
		MesUserDetails ralph = (MesUserDetails)userDetailsService.loadUserByUsername("ralph");

		Assert.assertTrue(user.getDepartment().getName().equals("Department A"));
		Assert.assertTrue(bob.getDepartment().getName().equals("Department B"));
		Assert.assertNull(ralph.getDepartment());
	}
}
