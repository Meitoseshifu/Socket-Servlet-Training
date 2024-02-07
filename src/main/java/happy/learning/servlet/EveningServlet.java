package happy.learning.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/evening")
public class EveningServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Optional.ofNullable(req.getParameter("name"))
                .ifPresent(name -> session.setAttribute("name", name));
        String name = Optional.ofNullable((String) session.getAttribute("name"))
                .orElse("BUDDY");
        PrintWriter writer = resp.getWriter();
        writer.write("Good Evening, " + name);
    }
}
