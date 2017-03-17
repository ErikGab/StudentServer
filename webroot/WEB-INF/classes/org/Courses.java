package servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.IOException;
import java.sql.*;

public class Courses extends HttpServlet{

	@Override
	public void init(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8), true);
		StringBuilder builder = new StringBuilder();

		try {

			Connection c = DriverManager.getConnection("jdbc:sqlite:courses.db");
			Statement s = c.createStatement();
			ResultSet rs= s.executeQuery("SELECT * FROM courses");
			builder.append("<!DOCTYPE html>\n");
			builder.append("<html>\n");
			builder.append("<head>\n");
			builder.append("  <title>JDBC excercise servlet!</title>\n");
			builder.append("</head>\n");
			builder.append("<body>\n");
			builder.append("<p>\n");
			builder.append("Courses:\n");
			builder.append("</p>\n");
			while (rs.next()){
				builder.append("<p>")
				 			.append(rs.getString("course_code"))
							.append("  -  ")
							.append(rs.getString("course_name"))
							.append("</p>");
			}
			builder.append("</body>\n");
			builder.append("</html>\n");
			out.println(builder.toString());
		} catch (SQLException e) {
			out.println("<em style=\"color: red;\">Database error: " + e.getMessage() + "</em>");
		}
		out.close();
	}

}
