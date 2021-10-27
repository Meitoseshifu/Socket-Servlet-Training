package happy.learning.socket.beans;

import java.net.URI;

public class Photo {
    private URI imgSrc;
    private URI location;
    private Integer contentLength;

    public Photo() {
    }

    public Photo(URI imgSrc) {
        this.imgSrc = imgSrc;
    }

    public URI getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(URI imgSrc) {
        this.imgSrc = imgSrc;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

}
