package happy.learning.socket;

import happy.learning.socket.beans.Photo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PhotoBuilder {
    private static final String LOCATION = "Location";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final URI NASA_URI = URI.create("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=12&api_key=DEMO_KEY");

    @SneakyThrows
    public List<Photo> requestImageSrc() {
        List<Photo> photos;
        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(NASA_URI.getHost(), 443);
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            printWriter.println("GET " + NASA_URI.getPath() + " HTTP/1.0");
            printWriter.println("HOST: " + NASA_URI.getHost());
            printWriter.println("Accept: application/json");
            printWriter.println();
            printWriter.flush();

            String json = bufferedReader.lines()
                    .filter(str -> str.startsWith("{"))
                    .findFirst()
                    .orElseThrow();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
            JsonNode jsonNodePhotos = jsonNode.get("photos");

            photos = StreamSupport.stream(jsonNodePhotos.spliterator(), false)
                    .map(node -> URI.create(node.get("img_src").textValue()))
                    .map(Photo::new)
                    .collect(Collectors.toList());
        }
        return photos;
    }

    @SneakyThrows
    public void requestLocation(List<Photo> photos, String host) {
        try (Socket socket = new Socket(host, 80);
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            photos.forEach(photo -> {
                printWriter.println("HEAD " + photo.getImgSrc().getPath() + " HTTP/1.1");
                printWriter.println("Host: " + photo.getImgSrc().getHost());
                printWriter.println();
                printWriter.flush();

                URI uri = bufferedReader.lines()
                        .filter(line -> line.startsWith(LOCATION))
                        .map(it -> it.substring(LOCATION.length() + 2))
                        .map(URI::create)
                        .findFirst()
                        .orElseThrow();
                photo.setLocation(uri);
            });
        }
    }

    @SneakyThrows
    public void requestContentLength(List<Photo> photos, String host) {
        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, 443);
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            photos.forEach(photo -> {
                printWriter.println("HEAD " + photo.getLocation().getPath() + " HTTP/1.1");
                printWriter.println("Host: " + photo.getLocation().getHost());
                printWriter.println();
                printWriter.flush();

                Integer length = bufferedReader.lines()
                        .filter(line -> line.startsWith(CONTENT_LENGTH))
                        .map(str -> str.substring(CONTENT_LENGTH.length() + 2))
                        .map(Integer::parseInt)
                        .findFirst()
                        .orElseThrow();
                photo.setContentLength(length);
            });
        }
    }
}
