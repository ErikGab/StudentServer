package student.servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.format.Format;
import org.format.FormatFactory;

public class DataServlet extends HttpServlet{
  public void init() throws ServletException{
    // Do initiation here...
  }
  public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
    String page;
    String requestformat = request.getParameter("format");
    PrintWriter out = response.getWriter();
    try {
      Format format = FormatFactory.getFormat(requestformat);
      page = format.getFakeTestData();
      out.println(page);
    } catch (Exception e) {
      String mess = "Aj aj aj, Hämta fakedata i önskat format sket sig\n" + e.getMessage();
      System.err.println(mess);
      out.println(mess);
    } finally {
      out.flush();
    }
    /* Set the content length to the
     * length of the stringbuilder
     * plus the additional newline
     * from println:
     */
    //response.setContentLength(page.length()+1);
  }
  public void destroy(){
    // cleanup etc here...
  }
}
