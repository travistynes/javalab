package p;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@SpringBootApplication()
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		logger.info("Application starting.");
		SpringApplication.run(Main.class, args);

		logger.info("Application started.");
	}

	/**
	 * Called after dependency injection is done to perform any initialization.
	 */
	@PostConstruct
	private void init() {
		logger.info("Application created. Running post initialization.");

		logger.info("Initialization complete.");
	}

	/**
	 * Called when the instance is being removed by the container.
	 */
	@PreDestroy
	private void shutdown() {
		logger.info("Application shutdown.");
	}
}
