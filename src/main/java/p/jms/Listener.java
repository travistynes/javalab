package p.jms;

import javax.jms.ConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    
    @JmsListener(destination="testq") // Destination = queue name
    public void rcv1(String msg) {
        log.info("Received on rcv1: " + msg);
    }
    
    @JmsListener(destination="testtopic", containerFactory="jmsConnectionFactory") // Destination = topic name
    public void rcv2(String msg) throws Exception {
        log.info("Received on rcv2: " + msg);
    }
    
    private static class JmsListenerContainerFactory {
        @Bean
        public DefaultJmsListenerContainerFactory jmsConnectionFactory(ConnectionFactory cf, DefaultJmsListenerContainerFactoryConfigurer config) {
            DefaultJmsListenerContainerFactory dcf = new DefaultJmsListenerContainerFactory();
            
            config.configure(dcf, cf);
            dcf.setPubSubDomain(true); // Set true to subscribe to a topic. Default is false and listens on a queue.
            
            return dcf;
        }
    }
}