import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EditPassengerServlet extends MainServlet {
    
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        String pob = request.getParameter("pob");
        String cit = request.getParameter("cit");
			
		if(name.length() == 0 || dob.length() == 0 || pob.length() == 0 || cit.length() == 0)
		{
			out.println("<h1>New Passenger Information</h1>"+
				"<form action='http://localhost:8085/servlet/EditPassengerServlet'>"+
				"<table>" +
					"<tr><th>Name</th><td><input type='text' name='name'></td></tr>"+
					"<tr><th>DOB</th><td><input type='date' name='dob'></td></tr>"+
					"<tr><th>POB</th><td><input type='text' name='pob'></td></tr>"+
					"<tr><th>Citizenship</th><td><input type='text' name='cit'></td></tr>"+
				"</table>" +
				"<p><input type='submit' value='Add Passenger Info'></p>"+
				"</form>");
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
		
		Connection conn = ConnectionManager.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		
		ResultSet rset = stmt.executeQuery("INSERT INTO PASSENGERS(ID, NAME, DOB, POB, CITIZENSHIP) VALUES(SEQ_PASSENGERS.nextval, '" + name + "', '" + dob + "', '" + pob + "', '" + cit + "')");
		rset = stmt.executeQuery("SELECT ID FROM PASSENGERS WHERE NAME='" + name + "' and DOB='" + dob + "' and POB='" + pob + "' and CITIZENSHIP='"+cit+"'");
		rset.next();
		
		out.println("<p> Passenger: " + name + " added with ID: " + rset.getString("ID")+"</p>");
		stmt.close();	
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

