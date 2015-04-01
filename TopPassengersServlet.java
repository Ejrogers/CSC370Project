import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TopPassengersServlet extends MainServlet {
    
	protected int getTab()
	{
		return 3;
	}
	
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        
		Connection conn = ConnectionManager.getInstance().getConnection();
		
        try{
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                 "SELECT * FROM ( " +
				 "SELECT PASSENGER_ID, NAME, NUM " +
				 "FROM (SELECT PASSENGER_ID, COUNT(ID) AS NUM " +
				 "FROM TICKETS GROUP BY PASSENGER_ID) A " +
				 "JOIN PASSENGERS ON PASSENGER_ID=PASSENGERS.ID ORDER BY NUM DESC)");	
		
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
		String table = "<table id='t01'>";
		table += "<tr><th>Name</th><th>Id</th><th>Flights Taken</th>";
		while (rset.next()) 
		{
			table += "<tr>";
			table +=
					"<td>"+rset.getString("NAME")+"</td>" +
					"<td>"+rset.getString("PASSENGER_ID")+"</td>" +
					"<td>"+rset.getString("NUM")+"</td>";
			table += "</tr>";
		}
		table += "</table>";
		out.println(table);
		out.println("</div><!--close content_item--> ");
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

