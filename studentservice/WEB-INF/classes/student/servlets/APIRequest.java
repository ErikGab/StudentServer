package student.servlets;
import javax.servlet.*;
import javax.servlet.http.*;

@FunctionalInterface
public interface APIRequest {
  public String respondToRequest(HttpServletRequest request, HttpServletResponse response);
}
