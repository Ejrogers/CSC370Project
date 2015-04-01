import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EditFlightServlet extends MainServlet {
    
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        String airline = request.getParameter("code");
        String src = request.getParameter("src");
        String sgate = request.getParameter("sgate");
        String dest = request.getParameter("dest");
        String dgate = request.getParameter("dgate");
        String d = request.getParameter("date");
        String atime = request.getParameter("atime");
        String dtime = request.getParameter("dtime");
        String status = request.getParameter("status");
        String plane = request.getParameter("plane");
			
		if(airline.length() == 0 || src.length() == 0 || sgate.length() == 0 
		|| dest.length() == 0 || dgate.length() == 0 || d.length() == 0
		|| atime.length() == 0 || dtime.length() == 0 || plane.length() == 0)
		{
			out.println("<h1>New Flight Information</h1>"+
				"<form action='http://localhost:8085/servlet/EditFlightServlet'>"+
					"<table>" +
					"<tr><th>Airline Code</th><td><input type='number' name='code'></td></tr>"+
					"<tr><th>Source</th><td><input type='text' name='src'></td></tr>"+
					"<tr><th>Source Gate</th><td><input type='text' name='sgate'></td></tr>"+
					"<tr><th>Destination</th><td><input type='text' name='dest'></td></tr>"+
					"<tr><th>Destination Gate</th><td><input type='text' name='dgate'></td></tr>"+
					"<tr><th>Date</th><td><input type='date' name='date'></td></tr>"+
					"<tr><th>Arrival Time</th><td><input type='time' name='atime'></td></tr>"+
					"<tr><th>Departure Time</th><td><input type='time' name='dtime'></td></tr>"+
					"<tr><th>Plane</th><td><input type='text' name='plane'></td></tr>"+
					"<tr><th>Status</th><td><input type='text' name='status'></td></tr>"+
				"</table>" +
				"<p><input type='submit' value='Add Flight Info'></p>"+
				"</form>");
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
		
		String[] adate = formatDate(d+"."+atime, "-", false);
		String[] ddate = formatDate(d+"."+dtime, "-", false);
		
		if(status.length() == 0)
			status = "Scheduled";
		
		Connection conn = ConnectionManager.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		
		ResultSet rset = stmt.executeQuery("INSERT INTO FLIGHTS(ID, AIRLINE, SRC, DEST, TIME, GATE, STATUS, TYPE, PLANE) "+
													"VALUES(SEQ_FLIGHTS.nextval, '" + airline + "', '" + src + "', '" + dest + "', to_date('" + ddate[0] + ":" + ddate[1] + "', 'dd/mm/yyyy:hh24:mi'), '" + sgate + "', '" + status + "', '" + "Outgoing" + "', '" + plane + "')");
													
		rset = stmt.executeQuery("INSERT INTO FLIGHTS(ID, AIRLINE, SRC, DEST, TIME, GATE, STATUS, TYPE, PLANE) "+
													"VALUES(SEQ_FLIGHTS.nextval, '" + airline + "', '" + src + "', '" + dest + "', to_date('" + ddate[0] + ":" + ddate[1] + "', 'dd/mm/yyyy:hh24:mi'), '" + dgate + "', '" + status + "', '" + "Incoming" + "', '" + plane + "')");
		out.println("<p> Flight added</p>");
		stmt.close();	
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

