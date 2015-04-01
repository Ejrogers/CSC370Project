import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PassengerServlet extends MainServlet {
	protected void makeTable(PrintWriter out, HttpServletRequest request)throws SQLException
	{
		String airline = request.getParameter("airline");
		String flight = request.getParameter("code");
		
		if(airline.equals("--------") || flight.length() == 0)
		{
			out.println("<script type=\"text/javascript\">alert('Please Fill Out All Field!');</script>");
			return;
		}
		
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT PASSENGERS.ID, NAME, DOB, POB, CITIZENSHIP, CLASS, BAGGAGE " +
                        "FROM PASSENGERS INNER JOIN TICKETS ON PASSENGERS.ID=TICKETS.PASSENGER_ID "+
						"WHERE AIRLINE="+airline.split("\\(")[1].split("\\)")[0]+" and FLIGHT="+flight);
						
		
        

            
			out.println("<h1> "+airline + " Flight: " + flight + " Passengers"+"</h1>");
			out.println("<table id='t01'>");
			out.println("<tr><th>Name</th><th>Id</th><th>DOB</th><th>POB</th><th>Citizenship</th><th>Class</th><th>Bag Id</th></tr>");
			while (rset.next()) {
				out.println("<tr>");
				out.print (
					"<td>"+rset.getString("NAME")+"</td>" +
					"<td>"+rset.getString("ID")+"</td>" +
					"<td>"+rset.getString("DOB").split(" ")[0]+"</td>" +
					"<td>"+rset.getString("POB")+"</td>" +
					"<td>"+rset.getString("CITIZENSHIP")+"</td>" +
					"<td>"+rset.getString("CLASS")+"</td>" +
					"<td>"+rset.getString("BAGGAGE")+"</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			
        stmt.close();
			
        ConnectionManager.getInstance().returnConnection(conn);
	}
	
   public String getServletInfo() {  return "Short description"; }
}

