package p.ws;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

public class SoapHandler implements SOAPHandler<SOAPMessageContext> {
    private static Logger log = Logger.getLogger("service");
    
    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }
    
    @Override
    public void close(MessageContext mc) {}
    
    @Override
    public boolean handleMessage(SOAPMessageContext c) {
        try {
            SOAPMessage message = c.getMessage();
            String soapMessage = this.getPrettyPrintFormat(new DOMSource(message.getSOAPBody()));
            
            log.info("[SOAP]\n" + soapMessage);
        } catch(Exception e) {
            log.debug("Error.", e);
            throw new RuntimeException(e);
        }
        
        return true;
    }
    
    @Override
    public boolean handleFault(SOAPMessageContext mc) {
        return true;
    }
    
    /**
     * Formats the SOAP message for pretty printing.
     * @param source
     * @return 
     */
    private String getPrettyPrintFormat(Source source) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(bos);
            t.transform(source, result);
            
            return bos.toString();
        } catch(Exception e) {
            log.debug("Pretty print error.", e);
            throw new RuntimeException(e);
        }
    }
}