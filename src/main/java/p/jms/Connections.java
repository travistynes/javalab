package p.jms;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Various JMS connection factory beans.
 */
@Configuration
public class Connections {
    private static final Logger log = LoggerFactory.getLogger(Connections.class);
    
    @Bean
    @Primary
    public ConnectionFactory cf1(@Value("${jms.mqa.url}") String brokerUrl) {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        
        return cf;
    }
}