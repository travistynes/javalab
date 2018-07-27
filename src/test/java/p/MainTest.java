package p;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

/**
 * The @ActiveProfile specifies the properties file that will be loaded.
 * When a profile is not set, it will default to the application.properties
 * and try to make connections to the external database.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"unittest"})
@SpringBootTest
public class MainTest {

	private static final Logger log = LoggerFactory.getLogger(MainTest.class);

	@Autowired
	private DataSource dataSource;
	
	// Runs before each test method.
	@Before
	public void before() {
		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		rdp.addScripts(
				new ClassPathResource("/sql/ddl.sql")
			      );
		rdp.execute(dataSource);
	}

	// Runs after each test method.
	@After
	public void after() {

	}

	@Test
	public void test1() throws Exception {
		try(Connection c = dataSource.getConnection()) {
			try(PreparedStatement ps = c.prepareStatement("insert into a (msg) values (?)")) {
				for(int a = 0; a < 3; a++) {
					ps.setString(1, "Message " + a);
					ps.executeUpdate();
				}
			}

			try(PreparedStatement ps = c.prepareStatement("select msg, ts from a")) {
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						log.info(rs.getString("msg") + ": " +  rs.getString("ts"));
					}
				}
			}
		}
	}
}
