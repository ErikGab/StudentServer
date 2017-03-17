package servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.IOException;

public class HelloWorld extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8), true);
			StringBuilder builder = new StringBuilder();
			builder.append("<!DOCTYPE html>\n");
			builder.append("<html>\n");
			builder.append("<head>\n");
			builder.append("  <title>Hello world servlet!</title>\n");
			builder.append("</head>\n");
			builder.append("<body>\n");
			builder.append("<p style=\"color: red;\">\n");
			builder.append("Hello World!\n");
			builder.append("</p>\n");
			builder.append("</body>\n");
			builder.append("</html>\n");
			out.println(builder.toString());
			out.close();
	}

}
