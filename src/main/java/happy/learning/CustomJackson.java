package happy.learning;

import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class CustomJackson {
    public static void main(String[] args) {
        var json = "{\n" +
                "    \"firstName\": \"Andrii\",\n" +
                "    \"lastName\": \"Shtramak\",\n" +
                "    \"email\": \"shtramak@gmail.com\"\n" +
                "};";

        var user = jsonToObject(json, User.class);
        System.out.println(user);
    }

    @SneakyThrows
    private static <T> T jsonToObject(String json, Class<T> userClass) {

        T user = userClass.getDeclaredConstructor().newInstance();
        Field[] declaredFields = userClass.getDeclaredFields();

        String result = json.replace("\n",
                        "")
                .replace("\"", "")
                .replace("{", "")
                .replace("};", "")
                .trim();

        String[] array = result.split(",");

        String name;

        for (Field declaredField : declaredFields) {
            name = declaredField.getName();
            for (String row : array) {
                if (row.contains(name)) {
                    int colonPosition = row.indexOf(":");
                    String value = row.substring(colonPosition + 2);
                    declaredField.setAccessible(true);
                    declaredField.set(user, value);
                }
            }
        }
        return user;
    }

    @Data
    static class User {
        private String firstName;
        private String lastName;
        private String email;
    }

}
