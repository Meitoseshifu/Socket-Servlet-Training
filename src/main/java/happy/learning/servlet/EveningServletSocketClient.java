package happy.learning.servlet;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class EveningServletSocketClient {
    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.0.189", 8080)) {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println("GET /evening?name=Socket HTTP/1.1");
            //printWriter.println("GET /evening HTTP/1.1");
            printWriter.println("Host: 192.168.0.189");
            //printWriter.println("Cookie: JSESSIONID=EFBA04A72C1753BE0999B5FCA20D0267");
            printWriter.println();
            printWriter.flush();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines().forEach(System.out::println);
        }
    }
}
