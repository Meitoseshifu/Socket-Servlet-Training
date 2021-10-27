package happy.learning.socket;

import happy.learning.socket.beans.Photo;
import lombok.SneakyThrows;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PhotoBuilder {
    private static final String LOCATION = "Location";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final URI NASA_URI = URI.create("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=12&api_key=DEMO_KEY");

    /**
     * use socket and get photos from nasa uri
     * @return photos
     */
    @SneakyThrows
    public List<Photo> requestImageSrc() {
        List<Photo> photos = new ArrayList<>();

        return photos;
    }

    /**
     * Use Socket, get Location uri for each photo
     * @param photos
     * @param host
     */
    @SneakyThrows
    public void requestLocation(List<Photo> photos, String host) {

    }

    /**
     * Use Socket, find content length for each photo
     * @param photos
     * @param host
     */
    @SneakyThrows
    public void requestContentLength(List<Photo> photos, String host) {

    }
}
