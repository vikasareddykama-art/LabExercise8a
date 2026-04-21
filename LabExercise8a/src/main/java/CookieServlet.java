// 8a. Build a servlet program to create a cookie to get your name
// and display greeting with visit count along with list of cookies and demonstrate expiry

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");

        int visitCount = 1;
        boolean newUser = true;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    name = c.getValue();
                }
                if (c.getName().equals("visitCount")) {
                    visitCount = Integer.parseInt(c.getValue());
                    visitCount++;
                    newUser = false;
                }
            }
        }

        // Create cookies
        Cookie nameCookie = new Cookie("username", name);
        Cookie countCookie = new Cookie("visitCount", String.valueOf(visitCount));

        // Set expiry time (30 seconds)
        nameCookie.setMaxAge(30);
        countCookie.setMaxAge(30);

        response.addCookie(nameCookie);
        response.addCookie(countCookie);

        // Output
        out.println("<html><body>");

        if (newUser) {
            out.println("<h2>Welcome " + name + "!</h2>");
        } else {
            out.println("<h2>Welcome back " + name + "!</h2>");
        }

        out.println("<p>You have visited this page " + visitCount + " times.</p>");

        out.println("<h3>List of Cookies:</h3>");

        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("<p>Name: " + c.getName() + 
                            " | Value: " + c.getValue() + "</p>");
            }
        }

        out.println("<p><i>Cookies expire in 30 seconds.</i></p>");

        out.println("</body></html>");
    }

    // Handle refresh (GET request)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}