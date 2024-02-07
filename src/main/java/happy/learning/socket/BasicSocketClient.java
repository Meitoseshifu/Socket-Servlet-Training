package happy.learning.socket;

import lombok.SneakyThrows;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class BasicSocketClient {
    private static final String HOST = BasicSocketServer.HOST;

    @SneakyThrows
    public static void main(String[] args) {
        /*try (var socket = new Socket("93.175.203.215", 18080)) {
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            var message = "Andriy Paliychuk \n";
            writer.write(message);
            writer.flush();
        }*/

        /*try (var socket = new DatagramSocket(8989)) {
            var message = "ne robe :(";
            var packet = new DatagramPacket(
                    message.getBytes(),
                    message.length(),
                    InetAddress.getByName("93.175.203.215"),
                    18080);
            socket.send(packet);
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
