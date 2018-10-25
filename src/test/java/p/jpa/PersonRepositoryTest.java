package p.jpa;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import java.sql.Date;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;

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
public class PersonRepositoryTest {

	private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PersonRepository personRepository;
	
	// Runs before each test method.
	@Before
	public void before() {
		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		rdp.addScripts(
				new ClassPathResource("sql/person.sql")
			      );
		rdp.execute(dataSource);
	}

	// Runs after each test method.
	@After
	public void after() {

	}

	@Test
	public void repositoryInitializedTest() throws Exception {
		assertTrue(personRepository.existsByName("bob"));
		assertFalse(personRepository.existsByName("noone"));
		assertEquals(0, personRepository.countByAge(1));
		assertEquals(1, personRepository.countByAge(10));
		assertEquals(10, personRepository.findByName("bob").getAge());
		assertEquals(1, personRepository.lookupQuery("mary").size());
	}
}
