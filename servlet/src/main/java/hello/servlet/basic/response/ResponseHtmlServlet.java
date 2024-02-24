package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //content-type, charset
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        response.getWriter().println("<html>");
        response.getWriter().println("<body>");
        response.getWriter().println("<div>안녕</div>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}
