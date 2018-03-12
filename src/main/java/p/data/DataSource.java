package p.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Database access beans.
 * @author travis
 */
@Configuration
public class DataSource {
    private static final Logger log = LoggerFactory.getLogger(DataSource.class);
    
    @Bean
    @Primary // Mark as default bean for @Autowired when there are multiple bean candidates.
    @ConfigurationProperties(prefix="db.c1")
    public javax.sql.DataSource ds1() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties(prefix="db.c2")
    public javax.sql.DataSource ds2() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties(prefix="db.ora.dev")
    public javax.sql.DataSource oracleDS() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @Primary // Mark as default bean for @Autowired when there are multiple bean candidates.
    public JdbcTemplate db1(@Qualifier("ds1") javax.sql.DataSource ds) {
        return new JdbcTemplate(ds);
    }
    
    @Bean
    public JdbcTemplate db2(@Qualifier("ds2") javax.sql.DataSource ds) {
        return new JdbcTemplate(ds);
    }
    
    @Bean
    public JdbcTemplate oracleDev(@Qualifier("oracleDS") javax.sql.DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
