package p;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    @Resource(name="db1")
    JdbcTemplate db1;
    
    @Resource(name="db2")
    JdbcTemplate db2;
    
    @Autowired
    A a;
    
    @Autowired
    JmsTemplate q;
    
    public static void main(String[] args) throws Exception {
        log.info("Startup.");
        SpringApplication.run(Main.class, args);
        
        log.info("Running.");
    }
    
    /**
     * Called after container has constructed the Main instance.
     */
    @PostConstruct
    private void init() {
        log.info("Init.");
        
        
        log.info("Done.");
    }
    
    private void dbquery() {
        List<Map<String, Object>> rs = db1.queryForList("select id, title, ts from public.events where id = ?", 2);
        rs.forEach((row) -> {
            log.info(row.get("id") + ", " + row.get("title") + ", " + row.get("ts"));
        });
    }
}

@Configuration
class A {
    private static final Logger log = LoggerFactory.getLogger(A.class);
    
    @Bean
    @Primary
    @Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE) // Return new instance each time bean is requested.
    public A a1() {
        
        return new A();
    }
}