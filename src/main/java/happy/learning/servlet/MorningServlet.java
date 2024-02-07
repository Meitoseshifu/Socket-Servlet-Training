package happy.learning.servlet;

import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@WebServlet("/morning")
public class MorningServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printClientMood(req);
        printClientCookie(req);
        printMorning(req, resp);
        resp.addCookie(new Cookie("SUPER_ID", UUID.randomUUID().toString()));
        /*PrintWriter writer = resp.getWriter();
        String name = Optional.ofNullable(req.getParameter("name")).orElse("my friend");
        String mood = Optional.ofNullable(req.getHeader("Sraka")).orElse("fine thanks");
        writer.println("Good morning, " + name + " " + mood);*/
    }

    private void printClientCookie(HttpServletRequest req) {
        Stream.of(req.getCookies()).forEach(c -> System.out.println(c.getName() + " " + c.getValue()));
    }

    private void printClientMood(HttpServletRequest req) {
        String name = Optional.ofNullable(req.getParameter("name")).orElse(req.getRemoteAddr());
        Optional.ofNullable(req.getHeader("X-Mood"))
                .ifPresent(mood -> System.out.println(name + " is feeling " + mood));
    }

    @SneakyThrows
    private void printMorning(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Optional<String> sessionName = Optional.ofNullable((String) session.getAttribute("name"));
        Optional<String> requestName = Optional.ofNullable(req.getParameter("name"));
        requestName
                .filter(name -> sessionName.isEmpty())
                .ifPresent(name -> session.setAttribute("name", name));

        PrintWriter writer = resp.getWriter();
        String name = requestName.or(() -> sessionName).orElse("my friend");
        writer.println("Good morning, " + name);
    }
}
