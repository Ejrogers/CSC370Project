import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EditPlaneServlet extends MainServlet {
    
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        String code = request.getParameter("code");
        String cap = request.getParameter("cap");
			
		if(code.length() == 0 || cap.length() == 0)
		{
			out.println("<h1>New Plane Information</h1>"+
				"<form action='http://localhost:8085/servlet/EditPlaneServlet'>"+
					"<table>" +
					"<tr><th>Code</th><td><input type='text' name='code'><br>"+
					"<tr><th>Capacity</th><td><input type='number' name='cap'><br>"+
				"</table>" +
				"<p><input type='submit' value='Add Plane Info'></p>"+
				"</form>");
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
		
		Connection conn = ConnectionManager.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		
		ResultSet rset = stmt.executeQuery("INSERT INTO PLANES(ID, CAPACITY) VALUES('" + code + "', '" + cap + "')");
		out.println("<p> Plane: " + code + " with capacity: " + cap + " added</p>");
		stmt.close();	
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

