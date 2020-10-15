package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;





/**
 * Servlet implementation class registerServlet
 */
@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String Name = request .getParameter("name");
		String Email = request.getParameter("email");
		String Password = request.getParameter("password");
        String Passes = "";
		
		//System.out.println("username: " + Name);
        //System.out.println("Email: " + Email);
        //System.out.println("Password:" + Password);
        
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(Password.getBytes(StandardCharsets.UTF_8));
		    String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
		    Passes = sha256;
		    //System.out.println(sha256);		
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		String URL = ("jdbc:postgresql://localhost:5432/Register");
		String user = "postgres";
		String pass = "Hatsunemiku";
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(URL, user, pass);
			//System.out.println("Got Connection");
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery("INSERT INTO accounts VALUES ('"+Name+"', '"+Email+"', '"+Passes+"')");
	        while (rs.next()) {
	            System.out.println(rs.getString("Data Upload Complete"));
	        }
		} catch (SQLException | ClassNotFoundException e) {
			 e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
