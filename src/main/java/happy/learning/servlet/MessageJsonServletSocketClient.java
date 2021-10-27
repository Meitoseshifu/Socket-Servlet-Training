package happy.learning.servlet;

import lombok.SneakyThrows;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageJsonServletSocketClient {
    @SneakyThrows
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"text\": \"Ups\",\n" +
                "    \"author\": \"Ups Master\"\n" +
                "};";

        try(Socket socket = new Socket("93.175.204.87", 8080)) {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println("POST /js HTTP/1.1");
            printWriter.println("Host: 93.175.204.87");
            printWriter.println("Content-Type: application/json");
            printWriter.println("Content-Length: " + json.length());
            printWriter.println();
            printWriter.println(json);
            printWriter.flush();
        }
    }
}
