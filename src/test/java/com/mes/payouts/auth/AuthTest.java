package com.mes.payouts.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
@AutoConfigureMockMvc
public class AuthTest {
	private static final Logger log = LoggerFactory.getLogger(AuthTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MockMvc mockMvc;
	
	// Runs before each test method.
	@Before
	public void before() throws Exception {

	}

	// Runs after each test method.
	@After
	public void after() throws Exception {

	}

	@Test
	public void publicResource() throws Exception {
		mockMvc.perform(get("/actuator/health"))
			.andExpect(status().isOk());
	}

	@Test
	public void noCredentials() throws Exception {
		// No auth header.
		mockMvc.perform(get("/api/v1/resource"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void badCredentials() throws Exception {
		// Invalid credentials.
		mockMvc.perform(get("/api/v1/resource").with(httpBasic("user", "bad_password")))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void validCredentialsNoData() throws Exception {
		mockMvc.perform(get("/api/v1/resource").with(httpBasic("user", "password")))
			.andExpect(status().isNoContent());
	}
}
