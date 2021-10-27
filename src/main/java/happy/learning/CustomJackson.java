package happy.learning;

import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * convert row json to User object without any framework help
 * do it on your own
 */
public class CustomJackson {
    public static void main(String[] args) {
        var json = "{\n" +
                "    \"firstName\": \"Andrii\",\n" +
                "    \"lastName\": \"Shtramak\",\n" +
                "    \"email\": \"shtramak@gmail.com\"\n" +
                "};";

        var user = jsonToObject(json, User.class);
    }

    private static <T> T jsonToObject(String json, Class<T> userClass) {
        return null;
    }

    @Data
    static class User {
        private String firstName;
        private String lastName;
        private String email;
    }

}
