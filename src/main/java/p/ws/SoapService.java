package p.ws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.HandlerChain;

import org.apache.log4j.Logger;

/**
 * See the entry in WEB-INF/web.xml. It is required to make the web service available.
 * WSDL url: http://localhost:8080/webapp/ws/SoapService?wsdl
 * 
 * For SOAP web services to be deployed and available they must be deployed on a
 * JavaEE compliant application server, ie. Apache TomEE.
 */
@Stateless
@WebService (
    name = "SoapService",
    serviceName = "SoapService",
    targetNamespace = "p.ws.SoapService"
)
@HandlerChain(file="soap_handler.xml")
public class SoapService {
    private static Logger log = Logger.getLogger(SoapService.class);
    
    @WebMethod
    public Wrapper getTime(String s) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            df.setTimeZone(TimeZone.getDefault());
            String ts = df.format(System.currentTimeMillis());

            log.info("Request: " + s);
            
            Wrapper wrap = new Wrapper();
            wrap.request = s;
            wrap.ts = ts;
            
            return wrap;
        } catch(Exception e) {
            log.debug("Error.", e);
            throw new RuntimeException(e);
        }
    }
    
    public static class Wrapper {
        public String request;
        public String ts;
    }
}