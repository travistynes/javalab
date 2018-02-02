package p.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Configuration
@RestController
public class RestService {
    private static final Logger log = LoggerFactory.getLogger(RestService.class);
    
    /**
     * Example rest service. 
     */
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", defaultValue="Unknown") String name) {
        String s = "Hello, " + name;
        log.info(s);
        
        return s;
    }
    
    /**
     * Example client calling rest service, converting JSON response to object.
     */
    @RequestMapping("/client")
    public String client() {
        RestTemplate rest = new RestTemplate();
        
        Quote q = rest.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        
        log.info(q.toString());
        
        return q.toString();
    }
    
    
    private static class Quote {
        private String type;
        private Value value;

        public void setType(String type) {
            this.type = type;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public Value getValue() {
            return value;
        }
        
        public String toString() {
            return "Type: " + type + ", Quote: " + value.quote;
        }
        
        
        private static class Value {
            String quote;

            public void setQuote(String quote) {
                this.quote = quote;
            }

            public String getQuote() {
                return quote;
            }
        }
    }
}