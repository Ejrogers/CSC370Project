import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TicketServlet extends MainServlet {
    
	
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
		
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String airline = request.getParameter("airline");
		String flight = request.getParameter("code");
		String clas = request.getParameter("class");
		String baggage = request.getParameter("bags");
		
		if(name.length() == 0 || id.length() == 0 || airline.equals("--------") || flight.length() == 0 || clas.length() == 0)
		{
			out.println("<h1>Buy Ticket</h1>"+
				"<form action='http://localhost:8085/servlet/TicketServlet'>"+
				"<table>" +
					"<tr><th>Name</th><td><input type='text' name='name'></td></tr>"+
					"<tr><th>ID</th><td><input type='text' name='id'></td></tr>"+
					"<tr><th>Airline</th><td><select name='airline'>"+
						"<option value='--------'>--------</option>"+
							getAirlines() + "</select></td></tr>"+
					"<tr><th>Flight Code</th><td><input type='text' name='code'></td></tr>"+
					"<tr><th>Class</th><td><select name='class'>"+
					"<option value='First'>First</option>"+
					"<option value='Coach'>Coach</option>"+
					"<option value='Infant'>Infant</option>"+
					"<option value='Special'>Special</option></select></td></tr>"+
					"<tr><th>Baggage</th><td><input type='text' name='bags'></td></tr>"+
				"</table>" +
				"<p><input type='submit' value='Buy Ticket'></p>" +
				"</form>");
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
        
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		Statement stmt = conn.createStatement();
		
		airline = airline.split("\\(")[1].split("\\)")[0];
		ResultSet rset = stmt.executeQuery("INSERT INTO BAGGAGE(ID, PASSENGER, AIRLINE, FLIGHT, CONTENT) VALUES(SEQ_BAGGAGE.nextval, '" + id + "', '" + airline + "', '" +flight + "', '" + baggage +"')");
		
		rset = stmt.executeQuery("SELECT ID FROM BAGGAGE WHERE AIRLINE='"+airline+"' and FLIGHT='"+flight+"' and PASSENGER='"+id+"'");
		rset.next();
		
		baggage = rset.getString("ID");
		
        rset = stmt.executeQuery(
                        "INSERT INTO TICKETS(ID, PASSENGER_ID, AIRLINE, FLIGHT, BAGGAGE, CLASS) " +
                        "VALUES(SEQ_TICKETS.nextval, '"+id+"','"+airline+"','"+flight+"', '"+baggage+"','"+clas+"')");
		
		stmt.close();	
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

