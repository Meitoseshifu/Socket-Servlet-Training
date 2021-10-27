package happy.learning.socket;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class BasicSocketClient {
    private static final String HOST = BasicSocketServer.HOST;

    @SneakyThrows
    public static void main(String[] args) {
        /*try (var socket = new Socket(HOST, 8899)) {
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            var message = "Hello from Chortkiv \n";
            writer.write(message);
            writer.flush();
        }*/

        try (InputStreamReader consoleInputStream = new InputStreamReader(System.in);
             BufferedReader bufferedReader = new BufferedReader(consoleInputStream)) {

            System.out.print("Enter message (q to quit): ");
            String message = bufferedReader.readLine();

            while (!message.equals("q")) {
                try (Socket socket = new Socket(HOST, 8899)) {
                    OutputStream outputStream = socket.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write(message);
                    bufferedWriter.flush();
                }
                System.out.print("Enter message (q to quit): ");
                message =  bufferedReader.readLine();
            }
        }
    }

}
