package happy.learning.socket;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class SendJsonViaSocketAndGetResponse {
    private static final String ADDRESS = "93.175.203.215";
    private static final int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket(ADDRESS, PORT)) {
            String json = "{\n" +
                    "    \"firstName\": \"Andriy\", \n" +
                    "    \"lastName\": \"Paliychuk\", \n" +
                    "    \"team\": \"Hoverla\", \n" +
                    "    \"trainigDaysPerWeek\": 5, \n" +
                    "    \"minutesPerDayTraining\": 120 \n" +
                    "}";
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println("POST /training/stats HTTP/1.1");
            printWriter.println("Host: 93.175.203.215:8080");
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

}
