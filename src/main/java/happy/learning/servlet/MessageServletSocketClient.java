package happy.learning.servlet;

import lombok.SneakyThrows;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageServletSocketClient {

    @SneakyThrows
    public static void main(String[] args) {
        try(Socket socket = new Socket("192.168.0.189", 8080)) {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println("POST /message?name=Dude&message=hello HTTP/1.1");
            printWriter.println("Host: 192.168.0.189");
            printWriter.println("Content-Type: text");
            printWriter.println();
            printWriter.flush();
        }
    }
}
