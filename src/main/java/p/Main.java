package p;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolingDataSource;
import javax.management.ObjectName;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static GenericObjectPool<PoolableConnection> connectionPool = null;
    
    static {
        try {
            Class.forName("org.postgresql.Driver"); // Load PostgreSQL driver.
            connectionPool = createPool(); // Create connection pool.
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) throws Exception {
        test();
    }
    
    public static void test() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getDefault());
        
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        
        try {
            c = connectionPool.borrowObject();
            s = c.prepareStatement("select current_timestamp ts");
            
            rs = s.executeQuery();
            
            if(rs.next()) {
                Timestamp ts = rs.getTimestamp("ts");
                String t = df.format(ts);
                log.info("Time: " + t);
            }
        } catch(Exception e) {
            log.error("Error.", e);
        } finally {
            try { if(rs != null) rs.close(); } catch(SQLException e) {}
            try { if(s != null) s.close(); } catch(SQLException e) {}
            try { if(c != null) c.close(); } catch(SQLException e) {}
        }
    }
    
    /*
    Creates connection pool.
    The following query will show all of the current connections in the database:
    select datname, usename, application_name, state, query from pg_stat_activity;
    */
    private static GenericObjectPool<PoolableConnection> createPool() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "travis";
        String password = "password";
        
        java.util.Properties p = new java.util.Properties();
        p.put("user", user);
        p.put("password", password);
        p.put("ApplicationName", "Postgresql Example");
        p.put("defaultRowFetchSize", "10"); // Default is 0 (fetch full result set at once). This can result in out of memory in large result sets.
        
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, p);
        PoolableConnectionFactory pConnectionFactory = new PoolableConnectionFactory(connectionFactory, new ObjectName("p", "connectionPool", "TestPool"));
        GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<PoolableConnection>(pConnectionFactory);
        pConnectionFactory.setPool(pool);
        
        pool.setMaxTotal(2);
        pool.setMinIdle(2);
        pool.setBlockWhenExhausted(true);
        pool.setMaxWaitMillis(5000); // How long to block waiting for a free connection in the pool.
        pool.preparePool(); // Force creation of getMinIdle() number of connections. Otherwise, they're created when needed.
        
        return pool;
    }
    
    // Back connection example, without pool.
    public static Connection connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "travis";
        String password = "password";
        
        java.util.Properties p = new java.util.Properties();
        p.put("user", user);
        p.put("password", password);
        p.put("ApplicationName", "Postgresql Example");
        p.put("defaultRowFetchSize", "10"); // Default is 0 meaning (fetch full result set at once). This can result in out of memory in large result sets.
        
        Connection c = DriverManager.getConnection(url, p);
        return c;
    }
}
