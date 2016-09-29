package p.servlet;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import org.apache.log4j.Logger;
import com.google.gson.Gson;

/*
loadOnStartup=1 causes the servlet to be loaded before any other
servlets with a larger value. init and destroy will be called on deploy/undeploy.
*/
@WebServlet (
    loadOnStartup=1,
    urlPatterns = {"/env"}
)
public class Environment extends HttpServlet {    
    public enum TYPE {
        UNKNOWN,
        TEST,
        QA,
        PROD
    }
    
    private static Logger log = Logger.getLogger(Environment.class);
    public static Environment.TYPE ENV = Environment.TYPE.UNKNOWN;
    public static String DNS_ALIAS = System.getProperty("system.alias");
    public static long DEPLOY_TIME = System.currentTimeMillis();
    
    /*
    Called on servlet initialization (on deployment).
    */
    public void init(ServletConfig config) throws ServletException {
        log.info("Application started.");
        
        String e = System.getProperty("system.type"); // One of: not set (null), TEST, QA, PROD.
        if(e == null) {
            log.info("system.type is not set; unknown environment.");
            Environment.ENV = Environment.TYPE.UNKNOWN;
        } else if(e.equalsIgnoreCase("TEST")) {
            Environment.ENV = Environment.TYPE.TEST;
        } else if(e.equalsIgnoreCase("QA")) { 
            Environment.ENV = Environment.TYPE.QA;
        } else if(e.equalsIgnoreCase("PROD")) {
            Environment.ENV = Environment.TYPE.PROD;
        } else {
            // Unknown environment.
            log.info("system.type is set, but was not recognized: " + e);
            Environment.ENV = TYPE.UNKNOWN;
        }
        
        log.info("Host environment set to: " + Environment.ENV);
    }
    
    /*
    Called when servlet is destroyed (on undeployment).
    */
    @Override
    public void destroy() {
        log.info("Application shutdown.");
    }
    
    /*
    Invoked on HTTP GET request.
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        
        // Return environment information.
        response.getWriter().write("Env: " + ENV.toString() + ", Deployed: " + DEPLOY_TIME);
    }
}