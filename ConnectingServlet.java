import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ConnectingServlet extends MainServlet {
    
	protected int getTab()
	{
		return 1;
	}
	
	protected void makeTable(PrintWriter out, HttpServletRequest request) throws SQLException
	{
        
		Connection conn = ConnectionManager.getInstance().getConnection();
		
        try{
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT SRC_ID, SRC_SRC, SRC_DEST, SRC_AL, SRC_AIRLINE, SRC_TIME, SRC_GATE, SRC_STATUS"+
						", DEST_ID, DEST_SRC, DEST_DEST, DEST_AL, DEST_AIRLINE, DEST_TIME, DEST_GATE, DEST_STATUS " +
                        "FROM CONNECTING");	
		
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
		out.println("<h1> Connecting Flights </h1>");
		String table;
		while (rset.next()) 
		{
		table = "<table>";
		table += "<tr><th>Id</th><th>Airline</th><th>Source</th><th>Destination</th><th>Time</th><th>Gate</th><th>Status</th></tr>";
			table += "<tr>";
			String[] date = formatDate(rset.getString("SRC_TIME"), "-", true);
			table +=
					"<td>"+rset.getString("SRC_ID")+"</td>" +
					"<td>"+rset.getString("SRC_AL")+"("+rset.getString("SRC_AIRLINE")+")"+"</td>" +
					"<td>"+rset.getString("SRC_SRC")+"</td>" +
					"<td>"+rset.getString("SRC_DEST")+"</td>" +
					"<td>"+date[0] + " " + date[1] +"</td>" +
					"<td>"+rset.getString("SRC_GATE")+"</td>" +
					"<td>"+rset.getString("SRC_STATUS")+"</td>";
			table += "</tr>";
			table += "<tr>";
			date = formatDate(rset.getString("DEST_TIME"), "-", true);
			table +=
					"<td>"+rset.getString("DEST_ID")+"</td>" +
					"<td>"+rset.getString("DEST_AL")+"("+rset.getString("DEST_AIRLINE")+")"+"</td>" +
					"<td>"+rset.getString("DEST_SRC")+"</td>" +
					"<td>"+rset.getString("DEST_DEST")+"</td>" +
					"<td>"+date[0] + " " + date[1] +"</td>" +
					"<td>"+rset.getString("DEST_GATE")+"</td>" +
					"<td>"+rset.getString("DEST_STATUS")+"</td>";
			table += "</tr>";
		table += "</table>";
		out.println(table);
		}
		out.println("</div><!--close content_item--> ");
	}
	
   public String getServletInfo() {  return "Airline Servlet"; }
}

