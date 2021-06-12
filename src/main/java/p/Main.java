package p;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
