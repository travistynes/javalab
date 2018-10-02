package p.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableRabbit
public class RabbitManager {
	private static final Logger log = LoggerFactory.getLogger(RabbitManager.class);

	@RabbitListener(queues = "${spring.rabbitmq.queueName}")
	public void processMessage(String message) {
		log.info("Got message: " + message);
	}
}
