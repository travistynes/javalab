package p;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    
    public static void main(String[] args) throws Exception {
        // Web service: http://www.webservicex.net/globalweather.asmx?op=GetCitiesByCountry
        String soap_request = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"><soap12:Body><GetCitiesByCountry xmlns=\"http://www.webserviceX.NET\"><CountryName>United States</CountryName></GetCitiesByCountry></soap12:Body></soap12:Envelope>";
        
        URL url = new URL("http://www.webservicex.net/globalweather.asmx");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/xml");
        con.setRequestProperty("charset", "UTF-8");
        con.setRequestProperty("Content-Length", String.valueOf(soap_request.length()));
        
        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        IOUtils.write(soap_request, out, "UTF-8");
        out.flush();
        IOUtils.closeQuietly(out);
        
        int rc = con.getResponseCode();
        
        //String s = IOUtils.toString(con.getInputStream(), "UTF-8"); System.out.println(s);
        XMLStreamReader r = XMLInputFactory.newInstance().createXMLStreamReader(con.getInputStream());
        
        r.nextTag(); // Advance to envelope
        r.nextTag(); // Advance to soap body
        r.nextTag(); // Advance to payload root node GetCitiesByCountryResponse
        
        // Turn SOAP response into object.
        JAXBContext jaxb = JAXBContext.newInstance(Cities.class);
        Unmarshaller un = jaxb.createUnmarshaller();
        JAXBElement<Cities> w = un.unmarshal(r, Cities.class);
        
        IOUtils.close(con);
        
        Cities cities = w.getValue();
        System.out.println("Result:\n" + cities.result);
    }
    
    @XmlRootElement(name = "GetCitiesByCountryResponse", namespace = "http://www.webserviceX.NET")
    static class Cities {
        @XmlElement(name = "GetCitiesByCountryResult", namespace = "http://www.webserviceX.NET")
        public String result;
    }
}
