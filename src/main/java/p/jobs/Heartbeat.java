package p.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class Heartbeat {
	private static final Logger log = LoggerFactory.getLogger(Heartbeat.class);

	@Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 60)
	public void tick() {
		// Runs every 60 minutes.
	}

	/*
	 * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
	 */
	@Scheduled(cron = "0 0 * * * ?")
	public void tock() {
		log.info("Top of the hour.");
	}
}
