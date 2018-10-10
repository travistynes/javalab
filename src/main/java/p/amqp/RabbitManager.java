package p.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class RabbitManager {
	private static final Logger log = LoggerFactory.getLogger(RabbitManager.class);

	private final String queueName = "testq";

	@RabbitListener(containerFactory = "localhostContainerFactory", queues = queueName)
	public void processMessage(String message) {
		log.info("Got message: " + message);
	}

	@Bean
	@ConfigurationProperties(prefix = "mq.a")
	public SimpleRabbitListenerContainerFactory localhostContainerFactory(@Qualifier("localhostConnectionFactory") ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
		f.setConnectionFactory(connectionFactory);

		return f;	
	}

	private static class ConnectionFactories {
		@Bean
		@ConfigurationProperties(prefix = "mq.a")
		public ConnectionFactory localhostConnectionFactory() {
			/*
			 * This works because CachingConnectionFactory has the matching 
			 * setters for Spring to inject the properties matching the configuration
			 * prefix.
			 */
			CachingConnectionFactory cf = new CachingConnectionFactory();
			return cf;
		}
	}
}
