package p.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Database access beans.
 *
 * @author travis
 */
@Configuration
public class DataSource {

	private static final Logger log = LoggerFactory.getLogger(DataSource.class);

	@Value("${db.pg.dev.url}")
	private String url;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "db.pg.dev")
	public DataSourceProperties pgDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "db.pg.dev.configuration")
	public HikariDataSource pgDS() {
		HikariDataSource ds = pgDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
		log.info("Created data source: " + url);

		return ds;
	}

	@Bean
	@Primary // Mark as default bean for @Autowired when there are multiple bean candidates.
	public JdbcTemplate db1(@Qualifier("pgDS") javax.sql.DataSource ds) {
		return new JdbcTemplate(ds);
	}
}
