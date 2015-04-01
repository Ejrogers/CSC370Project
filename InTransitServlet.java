import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InTransitServlet extends MainServlet {
    
	protected int getTab()
	{
		return 2;
	}
	
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        
		Connection conn = ConnectionManager.getInstance().getConnection();
		
        try{
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                 "SELECT * FROM INTRANSIT");	
		
		out.println("<div id='content'>");						
		tableHelper(out, rset);
		out.println("</div><!--close content-->   ");
        stmt.close();
        
        }
        catch(SQLException e) {
           out.println(e);
        }
		
		
					
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
	private void tableHelper(PrintWriter out, ResultSet rset) throws SQLException
	{
		out.println("<div class='content_item'> ");
		out.println("<h1> In Transit Passengers </h1>");
		String table = "<table>";
		while (rset.next()) 
		{
			String[] date = formatDate(rset.getString("TIME"), "-", true);
		table += "<tr><th>Airline</th><th>Flight</th><th>Name</th><th>Id</th><th>Destination</th><th>Arrival Time</th><th>Gate</th>";
			table += "<tr>";
			table +=
					"<td>"+rset.getString("A_NAME") + "(" + rset.getString("A_ID")+")</td>" +
					"<td>"+rset.getString("F_ID")+"</td>" +
					"<td>"+rset.getString("P_NAME")+"</td>" +
					"<td>"+rset.getString("P_ID")+"</td>" +
					"<td>"+rset.getString("DEST")+"</td>" +
					"<td>"+date[0] + " " + date[1]+"</td>" +
					"<td>"+rset.getString("GATE")+"</td>";
			table += "</tr>";
		}
		table += "</table>";
		out.println(table);
		out.println("</div><!--close content_item--> ");
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

