package com.mes.payouts.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Database access beans.
 *
 * @author travis
 */
@Configuration
public class DataSource {

	private static final Logger log = LoggerFactory.getLogger(DataSource.class);

	@Value("${db.ora.url}")
	private String url;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "db.ora")
	public DataSourceProperties oraDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "db.ora.configuration")
	public HikariDataSource oraDS() {
		HikariDataSource ds = oraDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
		log.info("Created data source: " + url);

		return ds;
	}

	@Bean
	@Primary // Mark as default bean for @Autowired when there are multiple bean candidates.
	public JdbcTemplate db1(@Qualifier("oraDS") javax.sql.DataSource ds) {
		return new JdbcTemplate(ds);
	}
}
