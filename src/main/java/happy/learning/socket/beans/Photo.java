package happy.learning.socket.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;

@Data
@AllArgsConstructor
public class Photo {
    private URI imgSrc;
    private URI location;
    private Integer contentLength;
}
