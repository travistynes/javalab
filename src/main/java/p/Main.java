package p;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    @Resource(name="db1")
    private JdbcTemplate jdbc;
    
    public static void main(String[] args) throws Exception {
        log.info("Starting.");
        SpringApplication.run(Main.class, args);
        
        log.info("Running.");
    }
    
    /**
     * Called after container has constructed the Main instance.
     */
    @PostConstruct
    private void init() {
        log.info("Init.");
        
       String ts = jdbc.queryForObject("select current_timestamp ts from dual", (rs, rn) -> {
	       return rs.getString("ts");
       }); 
        
       log.info("Connection to db good: " + ts);

       log.info("Done.");

    }
}
