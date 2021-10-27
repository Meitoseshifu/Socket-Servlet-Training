package happy.learning.socket;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class SendJsonViaSocketAndGetResponse {
    private static final String ADDRESS = "93.175.204.87";
    private static final int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {
        //todo структура HTTP запиту
        String json = "{\n" +
                "    \"firstName\": \"Andriy\",\n" +
                "    \"lastName\": \"Paliychuk\"\n" +
                "};";
        Socket socket = new Socket(ADDRESS, PORT);
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.println("GET /ping/current HTTP/1.1");
        printWriter.println("Host: 93.175.204.87:8080");
        printWriter.println("Content-Type: application/json");
        printWriter.println("Content-Length: " + json.length());
        printWriter.println();
        printWriter.println(json);
        printWriter.flush();
        InputStream inputStream = socket.getInputStream();

        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(in);
        bufferedReader.lines().forEach(System.out::println);
    }

}
