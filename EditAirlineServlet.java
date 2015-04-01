import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EditAirlineServlet extends MainServlet {
    
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        String name = request.getParameter("name");
        String website = request.getParameter("website");
			
		if(name.length() == 0 || website.length() == 0)
		{
			out.println("<h1>New Airline Information</h1>"+
				"<form action='http://localhost:8085/servlet/EditAirlineServlet'>"+
				"<table>" +
					"<tr><th>Name</th><td><input type='text' name='name'></td></tr>"+
					"<tr><th>Website</th><td><input type='text' name='website'></td></tr>"+
				"</table>" +
				"<p><input type='submit' value='Add Airline Info'></p>"+
				"</form>");
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
		
		Connection conn = ConnectionManager.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		
		ResultSet rset = stmt.executeQuery("INSERT INTO AIRLINES(ID, NAME, WEBSITE) VALUES(SEQ_AIRLINE.nextval, '" + name + "', '" + website + "')");
		out.println("<p> Airline: " + name + " with website: " + website + " added</p>");
		stmt.close();	
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

