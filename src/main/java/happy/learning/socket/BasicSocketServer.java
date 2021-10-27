package happy.learning.socket;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicSocketServer {
    public static final String HOST = getLocalHost();
    public static final int PORT = 8899;

    @SneakyThrows
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while(true) {
                try (Socket socket = serverSocket.accept()) {
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String message = bufferedReader.readLine();
                    InetAddress clientAddress = socket.getInetAddress();
                    System.out.print(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " ");
                    System.out.printf("[%s]", clientAddress.getHostAddress());
                    System.out.println(" -- " + message);
                }
            }
        }
    }

    @SneakyThrows
    public static String getLocalHost() {
        return InetAddress.getLocalHost().getHostAddress();
    }

}
