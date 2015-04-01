import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TimeServlet extends MainServlet {
	
	protected void makeTable(PrintWriter out, HttpServletRequest request)throws SQLException
	{
		String d = request.getParameter("date");
		String time = request.getParameter("time");
		if(time == null)
		{
			return;
		}
		
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		String[] date = formatDate(d+"."+time, "-", false);
		
			String hour = date[1].split(":")[0];
			String min = date[1].split(":")[1];
			
			String time1;
			
			String[] t = min_helper(hour, 0, 23);
			if(t[0] != null)
			{
				time1 = ":"+ t[0] + ":" + min;
			} else
			{
				time1 = ":00:00";
			}
			
			String time2;
			t = max_helper(hour, 0, 23);
			if(t[0] != null)
			{
				time2 = ":"+ t[0] + ":" + min;
			} else
			{
				time2 = ":23:59";
			}

			
			
			String title = date[0] + " " + date[1];
		
			time = " WHERE TIME between " +
						   "to_date('" + date[0] + time1 + "', 'dd/mm/yyyy:hh24:mi')" +
					  " and to_date('" + date[0] + time2 + "', 'dd/mm/yyyy:hh24:mi')";
					  
		
		
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT ID, SRC, DEST, TIME " +
                        "FROM FLIGHTS" + time + " and type='Incoming'");
		String table1 = tableHelper(out, "Incoming Flights Around " + title, rset);
         rset = stmt.executeQuery(
                        "SELECT ID, SRC, DEST, TIME " +
                        "FROM FLIGHTS" + time + " and type='Outing'");		
		String table2 = tableHelper(out, "Outgoing Flights Around " + title, rset);
		
        out.println("<div id='content'>"+
							table1 + "<br>" + 
							table2 +
					"</div><!--close content-->   ");

        stmt.close();
        ConnectionManager.getInstance().returnConnection(conn);
		
	}

	private String tableHelper(PrintWriter out, String title, ResultSet rset) throws SQLException
	{
		String table = "<div class='content_item'> ";
		table += "<h1> " + title + "</h1>";
		table += "<table id='t01'>";
		table += "<tr><th>Id</th><th>Source</th><th>Destination</th><th>Time</th></tr>";
		while (rset.next()) 
		{
			table += "<tr>";
			table +=
					"<td>"+rset.getString("ID")+"</td>" +
					"<td>"+rset.getString("SRC")+"</td>" +
					"<td>"+rset.getString("DEST")+"</td>" +
					"<td>"+formatDate(rset.getString("TIME"), "-", true)[1]+ "</td>";
			table += "</tr>";
		}
		table += "</table>";
		table += "</div><!--close content_item--> ";
		return table;
	}
	
	private String[] min_helper(String num, int min, int max)
	{
		int n = Integer.parseInt(num);
		String[] val = new String[2];
		if(n-1 >= min)
		{
			val[0] = "" + (n-1);
		} else
		{
			val[1] = "" + max;
		}
		return val;
	}
	private String[] max_helper(String num, int min, int max)
	{
		int n = Integer.parseInt(num);
		String[] val = new String[2];
		if(n+1 <= max)
		{
			val[0] = "" + (n+1);
		} else
		{
			val[1] = "" + min;
		}
		return val;
	}
	
   public String getServletInfo() {  return "Short description"; }
}

