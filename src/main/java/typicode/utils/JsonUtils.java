package typicode.utils;

import aquality.selenium.browser.AqualityServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonUtils {
    private static Gson gsonInstance;

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }

    public static <T> List<T> readListFromJson(String filePath, Class<T> clazz) {
        String json = FileUtils.readContent(filePath);
        return getGsonInstance().fromJson(json, new ListOf<>(clazz));
    }

    public static <T> List<T> readListFromString(String content, Class<T> clazz) {
        return getGsonInstance().fromJson(content, new ListOf<>(clazz));
    }

    public static <T> T getObjectFromJson(String filePath, Class<T> clazz) {
        return getGsonInstance().fromJson(FileUtils.readContent(filePath), clazz);
    }

    public static <T> T getObjectFromString(String content, Class<T> clazz) {
        return getGsonInstance().fromJson(content, clazz);
    }

    public static void writeListToJson(Object object, Path pathToJsonFile) {
        AqualityServices.getLogger().info("Write content to file with path: " + pathToJsonFile);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            String value = objectWriter.writeValueAsString(object);
            Files.writeString(pathToJsonFile, value);
        } catch (IOException e) {
            AqualityServices.getLogger().error("Write to file failed");
            throw new RuntimeException(e);
        }
    }

    private static class ListOf<T> implements ParameterizedType {
        private final Class<T> type;

        ListOf(Class<T> type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}