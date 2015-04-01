import java.sql.*;
import java.util.*;

public class ConnectionManager {
    private static ConnectionManager instance = null;
    private Stack connections;
    
    private ConnectionManager () {
        connections = new Stack();
        try { 
	        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
        } catch (Exception ex) { 
            System.out.println(ex); 
        }
    }

    public static ConnectionManager getInstance() {
        if (instance == null) instance = new ConnectionManager();
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        
        if (!connections.empty())
            conn = (Connection) connections.pop();
        else { //No one left in the stack, create a new one
            try {
                conn = DriverManager.getConnection 			 
			("jdbc:oracle:thin:@192.168.0.17:1521/ORCL", "ejrogers", "ejrogerspass"); 
            } catch (SQLException ex) { 
                System.out.println("SQLException: " + ex); 
            }
			if(conn != null)
			{
				System.out.println("You now have a database connection");
			} else
			{
				System.out.println("Database connection failed");
			}
        }
        return conn;
    }
    
    public void returnConnection(Connection conn) {
        if (conn != null) connections.push(conn);
    }      
}

