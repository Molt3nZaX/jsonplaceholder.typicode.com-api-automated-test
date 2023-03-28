package typicode.utils;

public enum HttpStatusCode {
    //2xx: Success
    OK(200, "OK"),
    CREATED(201, "Created"),

    //4xx: Client Error
    NOT_FOUND(404, "Not Found");

    private final int value;
    private final String description;

    HttpStatusCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return value + " " + description;
    }
}