package happy.learning.servlet;

import happy.learning.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/js")
public class MessageJsonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader bufferedReader = req.getReader();
        String json = bufferedReader.lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(json, Message.class);
        System.out.println(message.toString());
    }
}
