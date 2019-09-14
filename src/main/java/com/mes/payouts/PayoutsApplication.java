package com.mes.payouts;

import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class PayoutsApplication {
	private static final Logger log = LoggerFactory.getLogger(PayoutsApplication.class);

	public static void main(String[] args) {
		log.info("Starting PayoutsApplication");
		ConfigurableApplicationContext context = new SpringApplicationBuilder(PayoutsApplication.class).properties("spring.config.name:application").build().run(args);

		ConfigurableEnvironment environment = context.getEnvironment();

		log.info("PayoutsApplication config file loaded successfully: {}", environment.getProperty("spring.application.name"));
		log.info("Application started.");
	}

	/**
	 * Shutdown hook.
	 */
	@PreDestroy
	private void shutdown() {
		log.info("Application shutdown.");
	}
}
