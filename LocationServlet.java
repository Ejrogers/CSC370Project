import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LocationServlet extends MainServlet {
	
	protected void makeTable(PrintWriter out, HttpServletRequest request)throws SQLException
	{
		String location = request.getParameter("location");
		String command = "";
		if(location != null)
		{
			Connection conn = ConnectionManager.getInstance().getConnection();
		
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
							"SELECT FLIGHTS.ID, AIRLINE, NAME, SRC, DEST, TIME, GATE, STATUS " +
							"FROM FLIGHTS INNER JOIN AIRLINES ON AIRLINE=AIRLINES.ID " +
							"WHERE DEST = '" + location + "' and type='Incoming'");
			String table1 = tableHelper(out, location + " Incoming Flights", true, rset);
			
			rset = stmt.executeQuery(
							"SELECT FLIGHTS.ID, AIRLINE, NAME, SRC, DEST, TIME, GATE, STATUS " +
							"FROM FLIGHTS INNER JOIN AIRLINES ON AIRLINE=AIRLINES.ID " +
							"WHERE SRC = '" + location + "' and type='Outgoing'");
			String table2 = tableHelper(out, location + " Outgoing Flights", false, rset);
			
			out.println("<div id='content'>"+
								table1 + "<br>" + 
								table2 +
						"</div><!--close content-->   ");
			stmt.close();
			
			
		
        ConnectionManager.getInstance().returnConnection(conn);
		
		}
			
		
	}
	
	private String tableHelper(PrintWriter out, String title, boolean src, ResultSet rset) throws SQLException
	{
		String table = "<div class='content_item'> ";
		table += "<h1> " + title + "</h1>";
		table += "<table id='t01'>";
		table += "<tr><th>Id</th>";
		table += "<th>Airline</th>";
		if(src)
			table += "<th>Source</th><th>Arrival Time</th>";
		else
			table += "<th>Destination</th><th>Departure Time</th>";
		table += "<th>Gate</td>";
		table += "<th>Status</td></tr>";
		while (rset.next()) 
		{
			table += "<tr>";
			table +=
					"<td>"+rset.getString("ID")+"</td>" +
					"<td>"+rset.getString("NAME")+"("+rset.getString("AIRLINE")+")"+"</td>";
			if(src)
				table += "<td>"+rset.getString("SRC")+"</td>";
			else
				table += "<td>"+rset.getString("DEST")+"</td>";
			String[] date = formatDate(rset.getString("TIME"), "-", true);
			table += "<td>"+date[0] + " " + date[1] + "</td>";
			table += "<td>"+rset.getString("GATE") + "</td>";
			table += "<td>"+rset.getString("STATUS") + "</td>";
			
			table += "</tr>";
		}
		table += "</table>";
		table += "</div><!--close content_item--> ";
		return table;
	}
	
	
   public String getServletInfo() {  return "Short description"; }
}

