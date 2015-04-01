import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AirlineServlet extends MainServlet {
    
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
            
		String airline = request.getParameter("airline");
		
		String id = airline.split("\\(")[1].split("\\)")[0];
		String command = "";
		String title = "";
		if(airline != null)
		{
			command = " WHERE airline = '" + id + "'";
		} else 
			airline = "";
			
		Connection conn = ConnectionManager.getInstance().getConnection();
		
        try{
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT ID, SRC, DEST, TIME, GATE, STATUS " +
                        "FROM FLIGHTS" + command + " and type='Incoming'");		
		String table1 = tableHelper(out, airline + " Incoming Flights", rset, "Arrival");
		
		
		
         rset = stmt.executeQuery(
                        "SELECT ID, SRC, DEST, TIME, GATE, STATUS " +
                        "FROM FLIGHTS" + command + " and type='Outgoing'");
		String table2 = tableHelper(out, airline + " Outgoing Flights", rset, "Departure");
		
		out.println("<div id='content'>"+
							table1 + "<br>" + 
							table2 +
					"</div><!--close content-->   ");
        stmt.close();
        
        }
        catch(SQLException e) {
           out.println(e);
        }
		
		
					
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
	private String tableHelper(PrintWriter out, String title, ResultSet rset, String time) throws SQLException
	{
		String table = "<div class='content_item'> ";
		table += "<h1> " + title + "</h1>";
		table += "<table id='t01'>";
		table += "<tr><th>Id</th><th>Source</th><th>Destination</th><th>" + time + " Time</th><th>Gate</th><th>Status</th></tr>";
		while (rset.next()) 
		{
			String[] date = formatDate(rset.getString("TIME"), "-", true);
			table += "<tr>";
			table +=
					"<td>"+rset.getString("ID")+"</td>" +
					"<td>"+rset.getString("SRC")+"</td>" +
					"<td>"+rset.getString("DEST")+"</td>" +
					"<td>"+date[0] + " " + date[1]+"</td>" +
					"<td>"+rset.getString("GATE")+"</td>" +
					"<td>"+rset.getString("STATUS")+"</td>";
			table += "</tr>";
		}
		table += "</table>";
		table += "</div><!--close content_item--> ";
		return table;
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

