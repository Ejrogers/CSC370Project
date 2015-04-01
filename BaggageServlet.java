import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class BaggageServlet extends MainServlet {
    
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String ticket = request.getParameter("code");
			
		String title = "";
		if(name.length() == 0 || id.length() == 0 || ticket.length() == 0)
		{
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT CONTENT " +
                        "FROM TICKETS INNER JOIN BAGGAGE ON BAGGAGE.ID=TICKETS.BAGGAGE "+
						"WHERE TICKETS.ID="+ticket+ " and TICKETS.PASSENGER_ID=" + id);
		
        if(rset.next())
		{
			out.println("<div id='content'>"+
							"<div class='content_item'> " + 
								"<p>" + name + " Baggage: " + rset.getString("CONTENT") + "</p>" + 
							"</div><!--close content_item--> " +
					"</div><!--close content-->   ");
		}
		
		
        stmt.close();
					
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

