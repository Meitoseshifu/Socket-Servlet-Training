package happy.learning.servlet;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class MorningServletSocketClient {

    @SneakyThrows
    public static void main(String[] args) {

        try (Socket socket = new Socket("192.168.0.189", 8080)) {
            /**
             * Send a message to MorningServlet via BufferedWriter
             * Always send with Cookie or comment printClientCookie in MorningServlet
             */
            System.out.println("Sending Request...");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("GET /morning HTTP/1.1\n");
            //bufferedWriter.write("GET /morning?name=Andrew HTTP/1.1\n");
            bufferedWriter.write("Host: 192.168.0.189\n");
            bufferedWriter.write("X-Mood: Enlightened\n");
            bufferedWriter.write("Cookie: JSESSIONID=5E38AC6A6D7A23C53113697790653FFC\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

            /**
             * Read answer from MorningServlet
             */
            System.out.println("Reading Response:");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader in = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(in);
            bufferedReader.lines().forEach(System.out::println);
        }
    }
}
