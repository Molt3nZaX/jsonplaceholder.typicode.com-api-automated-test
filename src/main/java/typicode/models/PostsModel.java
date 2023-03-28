package typicode.models;

import lombok.Data;

@Data
public class PostsModel {
    private int userId;
    private int id;
    private String title;
    private String body;
}