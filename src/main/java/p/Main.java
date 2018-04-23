package p;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		log.info("Environment:");
		System.getProperties().forEach((k, v) -> {
			log.info(k + ": " + v);
		});
	}
}
