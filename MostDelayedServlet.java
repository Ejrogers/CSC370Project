import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MostDelayedServlet extends MainServlet {
    
	protected int getTab()
	{
		return 4;
	}
	
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        
		Connection conn = ConnectionManager.getInstance().getConnection();
		
        try{
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                 "SELECT SRC, DEST, AIRLINE, NUMDELAYS, NAME " +
				 "FROM MOSTDELAYS A " +
				 "WHERE NUMDELAYS=(SELECT MAX(NUMDELAYS) " +
				 "FROM MOSTDELAYS B WHERE B.SRC = A.SRC and B.DEST = A.DEST)");	
		
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
		while (rset.next()) 
		{
		table += "<tr><th>Airline</th><th>Source</th><th>Destination</th><th>Delayed Count</th>";
			table += "<tr>";
			table +=
					"<td>"+rset.getString("NAME")+ "(" + rset.getString("AIRLINE")+")</td>" +
					"<td>"+rset.getString("SRC")+"</td>" +
					"<td>"+rset.getString("DEST")+"</td>" +
					"<td>"+rset.getString("NUMDELAYS")+"</td>";
			table += "</tr>";
		}
		table += "</table>";
		out.println(table);
		out.println("</div><!--close content_item--> ");
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

