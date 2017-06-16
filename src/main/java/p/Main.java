package p;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    
    public static void main(String[] args) throws Exception {
        log.info("Starting application.");
        SpringApplication.run(Main.class, args);
    }
    
    public boolean truthtest(boolean b) {
        return b;
    }
}
