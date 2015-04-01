import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;

public class MainServlet extends HttpServlet {
	
	protected int getTab()
	{
		return 0;
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
		
		String airlineList = "";
		try
		{
			airlineList = getAirlines();
		}catch (SQLException ex)
		{
			out.println(ex);
		}
		
		
		
		
		out.println("<html>");
            out.println("<head>");
            out.println("<title>Evan Rogers Flight Managment</title>");
  
            out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/css/style.css' />" +
						"<script type='text/javascript' src='" + request.getContextPath() + "js/jquery.min.js'></script>"+
						"<script type='text/javascript' src='" + request.getContextPath() + "js/image_slide.js'></script>");
            out.println("</head>");
            out.println("<body>");
		
		
		out.println("<link rel='stylesheet' type='text/css' href='css/style.css' />"+
  "<script type='text/javascript' src='js/jquery.min.js'></script>"+
  "<script type='text/javascript' src='js/image_slide.js'></script>");

		
		out.println("<div id='main'><div id='header'><div id='banner'><div id='welcome'><h1>Evan Rogers</h1></div><div id='welcome_slogan'><h1>Flight Management Inc.</h1></div></div></div>");
		

	out.println(
	"<div id='menubar'>"+
	"<ul id='menu'>" +
	
		getCurrent("http://localhost:8085/servlet/MainServlet", "Home", 0) + 
		getCurrent("http://localhost:8085/servlet/ConnectingServlet", "Connecting Flights", 1) + 
		getCurrent("http://localhost:8085/servlet/InTransitServlet", "Passengers In-Transit", 2) + 
		getCurrent("http://localhost:8085/servlet/TopPassengersServlet", "Top Passengers", 3) + 
		getCurrent("http://localhost:8085/servlet/MostDelayedServlet", "Most Delayed Flights", 4) + 
      "</ul></div>"+
    
	"<div id='site_content'>"+		

	  "<div class='sidebar_container'>"+       
		"<div class='sidebar'>"+
          "<div class='sidebar_item'>"+
            "<h2>Edit</h2>"+
            "<form method='GET' action='http://localhost:8085/servlet/AddServlet' >"+
                "Edit: <select name='add' onchange='this.form.submit()'>"+
				"<option value='--------'>--------</option>"+
				"<option value='Ticket'>Ticket</option>" +
				"<option value='Passenger'>Passenger</option>" +
				"<option value='Airline'>Airline</option>" +
				"<option value='Flight'>Flight</option>" +
				"<option value='Plane'>Plane</option></select>" +
				"<noscript><input type='submit' value='Submit'></noscript><br><br>"+
            "</form>"+
          "</div><!--close sidebar_item-->"+
        "</div><!--close sidebar-->"+
		"<div class='sidebar'>"+
          "<div class='sidebar_item'>"+
            "<h2>Airline Flights</h2>"+
            "<form method='GET' action='http://localhost:8085/servlet/AirlineServlet' >"+
                "Airline: <select name='airline' onchange='this.form.submit()'>"+
				"<option value='--------'>--------</option>"+
				airlineList + "</select> "+
				"<noscript><input type='submit' value='Submit'></noscript><br><br>"+
            "</form>"+
          "</div><!--close sidebar_item-->"+
        "</div><!--close sidebar-->"+		
		"<div class='sidebar'>"+
          "<div class='sidebar_item'>"+
            "<h2>City Search</h2>"+
            "<form method='GET' action='http://localhost:8085/servlet/LocationServlet' >"+
                "City: <br><input type='text' name='location' value=''/> <br>"+
                "<input type='submit' value='Search'>    <br><br>"+
            "</form>"+         
		  "</div><!--close sidebar_item-->"+ 
        "</div><!--close sidebar-->"+
		"<div class='sidebar'>"+
          "<div class='sidebar_item'>"+
            "<h2>Flight Search</h2>"+
            "<form action='http://localhost:8085/servlet/TimeServlet'>"+
				"Date: <input type='date' name='date'>"+
				"Time: <input type='time' name='time'>"+
				"<input type='submit' value='Search'><br><br>"+
			"</form>"+
		  "</div><!--close sidebar_item--> "+
        "</div><!--close sidebar-->  "+
		"<div class='sidebar'>"+
          "<div class='sidebar_item'>"+
            "<h2>Passengers</h2>"+
            "<form action='http://localhost:8085/servlet/PassengerServlet'>"+
				"Airline: <select name='airline'>"+
				"<option value='--------'>--------</option>"+
				airlineList + "</select><br>" +
				"Flight #: <input type='text' name='code' value=''/>"+
				"<input type='submit' value='Search'><br><br>"+
			"</form>"+
		  "</div><!--close sidebar_item--> "+
        "</div><!--close sidebar--> "+ 		
        "<div class='sidebar'>"+
          "<div class='sidebar_item'>"+
            "<h2>Find Baggage</h2>"+
            "<form action='http://localhost:8085/servlet/BaggageServlet'>"+
				"Name: <input type='text' name='name' value=''/> <br>"+
				"Id:<br><input type='text' name='id' value=''/> <br>"+
				"Ticket #: <input type='text' name='code' value=''/>"+
				"<input type='submit' value='Search'><br>" +
			"</form>"+
          "</div><!--close sidebar_item--> "+
        "</div><!--close sidebar-->"+
       "</div><!--close sidebar_container-->	"+
	
	  "<div class='slideshow'>"+
	    "<ul class='slideshow'>"+
          "<li class='show'><img width='680' height='250' src='" + request.getContextPath() + "/images/home_1.jpg'/></li>"+
          "<li><img width='680' height='250' src='" + request.getContextPath() + "/images/home_2.jpg'/></li>"+
        "</ul>"+
      "</div>	");
	  
	  try
	  {
		makeTable(out, request);
	  }catch (SQLException ex)
	  {
           out.println(ex);
	  }
	  
	  out.println("</div><!--close main-->");
            out.println("</body>");
		
    }
	
	protected String getAirlines() throws SQLException
	{
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		String airlines = "";
        
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT ID, NAME FROM AIRLINES");	
		while(rset.next())
		{
			String a = rset.getString("NAME") + "(" + rset.getString("ID") + ")";
			airlines += "<option value='" + a + "'>" + a + "</option>";
		}
        stmt.close();
        ConnectionManager.getInstance().returnConnection(conn);
		return airlines;
	}
	
	protected String getFlights(int airline) throws SQLException
	{
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		String flights = "";
        
		Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT ID FROM FLIGHT WHERE AIRLINE="+airline);	
		while(rset.next())
		{
			flights += "<option value='" + rset.getString("ID") + "'>" + rset.getString("ID") + "</option>";
		}
        stmt.close();
        ConnectionManager.getInstance().returnConnection(conn);
		return flights;
	}
	
	protected String[] formatDate(String t, String delim, boolean parseTime)
	{
		String[] date = new String[2];
		
		String year = t.split(delim)[0];
		String month = t.split(delim)[1];
		String day = t.split(delim)[2].split("\\.")[0];
		
		date[0] = day + "/" + month + "/" + year;	
		if(parseTime)
			date[1] = String.format("%02d", Integer.parseInt(t.split("\\.")[1])) + ":" + String.format("%02d", Integer.parseInt(t.split("\\.")[2]));
		else
			date[1] = t.split("\\.")[1];
		return date;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
        processRequest(request, response);
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
	
	public String getServletInfo(){return "Home Screen";}
	protected void makeTable(PrintWriter out, HttpServletRequest request)throws SQLException{}
	
	
	
	private String getCurrent(String href, String title, int val)
	{
		String ret = "<li";
		if(getTab() == val)
			ret += " class='current'";
        return ret + "><a href='" + href + "'>" + title + "</a></li>";
	}
}

