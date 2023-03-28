package typicode.utils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import typicode.models.PostsModel;
import typicode.models.UsersModel;

import java.util.List;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static typicode.utils.ApiUtils.getRequest;
import static typicode.utils.ApiUtils.postRequest;
import static typicode.utils.HttpStatusCode.*;
import static typicode.utils.JsonUtils.getObjectFromString;
import static typicode.utils.JsonUtils.readListFromString;
import static typicode.utils.TestDataUtils.getTestData;

public class ApiAppRequests implements DataConstantsUtils {
    public static List<PostsModel> getAllPosts() {
        getLogger().info("GET request to \"/posts\" and response");
        HttpResponse<JsonNode> response = getRequest(getTestData().getBaseUrl() + getTestData().getPosts());
        assert response.getStatusText().equals(OK.getDescription()) : "Request status is not 200";
        return readListFromString(response.getBody().toString(), PostsModel.class);
    }

    public static PostsModel getPostsRequestById99() {
        getLogger().info("GET request to \"/posts/99" + "\" and response");
        HttpResponse<JsonNode> response = getRequest(getTestData().getBaseUrl() + getTestData().getPosts() + getTestData().getId99());
        assert response.getStatusText().equals(OK.getDescription()) : "Request status is not 200";
        return getObjectFromString(response.getBody().toString(), PostsModel.class);
    }

    public static JsonNode getPostsRequestById150() {
        getLogger().info("GET request to \"/posts/150" + "\" and response");
        HttpResponse<JsonNode> response = getRequest(getTestData().getBaseUrl() + getTestData().getPosts() + getTestData().getId150());
        assert response.getStatusText().equals(NOT_FOUND.getDescription()) : "Request status is not 404";
        return response.getBody();
    }

    public static PostsModel postPostsRequest() {
        getLogger().info("POST request to \"/posts\"");
        PostsModel randomPostData = JsonUtils.getObjectFromJson(PATH_TO_RANDOM_POST_DATA, PostsModel.class);
        HttpResponse<PostsModel> response = postRequest(getTestData().getBaseUrl() + getTestData().getPosts()).body(randomPostData).asObject(PostsModel.class);
        assert (response.getStatusText().equals(CREATED.getDescription())) : "Request status is not 201";
        return response.getBody();
    }

    public static List<UsersModel> getAllUsers() {
        getLogger().info("GET request to \"/users\" and response");
        HttpResponse<JsonNode> response = getRequest(getTestData().getBaseUrl() + getTestData().getUsers());
        assert response.getStatusText().equals(OK.getDescription()) : "Request status is not 200";
        return readListFromString(response.getBody().toString(), UsersModel.class);
    }

    public static UsersModel getUserRequestById(int id) {
        getLogger().info("GET request to \"/users/" + id);
        HttpResponse<JsonNode> response = getRequest(getTestData().getBaseUrl() + getTestData().getUsers() + id);
        assert response.getStatusText().equals(OK.getDescription()) : "Request status is not 200";
        return getObjectFromString(response.getBody().toString(), UsersModel.class);
    }
}