package p.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import org.apache.log4j.Logger;

@WebServlet (
    urlPatterns = {"/dbrequest"}
)
public class DBRequest extends HttpServlet {    
    private static Logger log = Logger.getLogger(DBRequest.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        
        String databaseName = request.getParameter("databaseName");
        String dbtype = request.getParameter("dbtype");
        
        if(databaseName == null || dbtype == null) {
            throw new ServletException("Missing required parameters.");
        }
        
        log.info("[" + request.getRemoteAddr() + "] Database request (" + dbtype + ") Name: " +  databaseName);
    }
}
