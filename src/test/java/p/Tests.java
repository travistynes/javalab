package p;

import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.h2.jdbcx.JdbcConnectionPool;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tests {

	private static final Logger log = LoggerFactory.getLogger(Tests.class);

	private DataSource ds;

	@Before
	public void setup() throws Exception {
		log.info("Setting up H2 db.");

		ds = JdbcConnectionPool.create("jdbc:h2:mem:testdb;MODE=Oracle", "sa", "");

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql/create_table_a.sql");
		String sql = IOUtils.toString(is, "UTF-8");

		ds.getConnection().prepareStatement(sql).execute();
	}

	@After
	public void cleanup() throws Exception {
		log.info("Cleanup.");
		ds.getConnection().prepareStatement("SHUTDOWN").execute();
	}

	@Test
	public void test1() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement("select x from a");
			rs = ps.executeQuery();

			Assert.assertFalse(rs.next());
		}
		finally {
			try {
				if (con != null) {
					con.close();
				}
			}
			catch (Exception ex) {
			}
			try {
				if (ps != null) {
					ps.close();
				}
			}
			catch (Exception ex) {
			}
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (Exception ex) {
			}
		}
	}
}
