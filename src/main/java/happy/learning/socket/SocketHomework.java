package happy.learning.socket;

import happy.learning.socket.beans.Photo;
import happy.learning.socket.beans.Picture;
import happy.learning.socket.beans.ToJson;
import happy.learning.socket.beans.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SocketHomework {
    private static final URI BOBOCODE_URI = URI.create("https://bobocode.herokuapp.com/nasa/pictures");

    @SneakyThrows
    public static void main(String[] args) {
        PhotoBuilder photoBuilder = new PhotoBuilder();
        List<Photo> photos = photoBuilder.requestImageSrc();
        photoBuilder.requestLocation(photos, photos.get(0).getImgSrc().getHost());
        photoBuilder.requestContentLength(photos, photos.get(0).getLocation().getHost());
        sendToBobocode(photos.stream().max(Comparator.comparing(Photo::getContentLength)).orElseThrow());
    }

    @SneakyThrows
    static void sendToBobocode(Photo biggestPhoto) {
        ToJson toJson = new ToJson(
                new Picture(biggestPhoto.getImgSrc().toString(), biggestPhoto.getContentLength().toString()),
                new User("Andriy", "Paliychuk")
        );
        ObjectWriter objectWriter = new ObjectMapper().writerFor(ToJson.class);
        String json = objectWriter.writeValueAsString(toJson);

        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(BOBOCODE_URI.getHost(), 443);
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            printWriter.println("POST " + BOBOCODE_URI.getPath() + " HTTP/1.1\r");
            printWriter.println("Host: " + BOBOCODE_URI.getHost() + "\r");
            printWriter.println("Content-Length: " + json.length() + "\r");
            printWriter.println("Content-Type: application/json;charset=UTF-8\r");
            printWriter.println("\r");
            printWriter.println(json + "\r");
            printWriter.println("\r");
            printWriter.println("\r");
            printWriter.flush();

            String response = bufferedReader.lines()
                    .collect(Collectors.joining("\n"));
            System.out.println(response);
        }
    }



}