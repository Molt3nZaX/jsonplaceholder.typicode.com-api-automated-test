package typicode;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import typicode.models.PostsModel;
import typicode.models.UsersModel;
import typicode.utils.DataConstantsUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static org.testng.Assert.assertEquals;
import static typicode.utils.ApiAppRequests.*;
import static typicode.utils.JsonUtils.*;
import static typicode.utils.TestDataUtils.getTestData;

public class TypicodeTest implements DataConstantsUtils {
    @Test(testName = "Typicode test")
    public void typicodeTest() {
        getLogger().info("Step 1: Send GET Request to get all posts (/posts).");
        List<PostsModel> allPostsList = getAllPosts();
        List<PostsModel> copyObjectsFromRequestList = new ArrayList<>(allPostsList);
        copyObjectsFromRequestList.sort(Comparator.comparingInt(PostsModel::getId));
        assertEquals(allPostsList, copyObjectsFromRequestList, "Posts are not sorted ascending (by id).");

        getLogger().info("Step 2: Send GET request to get post with id=99 (/posts/99).");
        PostsModel objectFromRequest = getPostsRequestById99();
        List<PostsModel> postsDataList = readListFromJson(PATH_TO_POSTS_DATA, PostsModel.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(objectFromRequest.getId(), postsDataList.get(98).getId(), "Incorrect \"id\" value. It's should be 10");
        softAssert.assertEquals(objectFromRequest.getUserId(), postsDataList.get(98).getUserId(), "Incorrect \"userId\" value. It's should be 99");
        softAssert.assertFalse(objectFromRequest.getBody().isEmpty(), "\"body\" value is empty");
        softAssert.assertFalse(objectFromRequest.getTitle().isEmpty(), "\"tittle\" value is empty");
        softAssert.assertAll("Some values are not equal");

        getLogger().info("Step 3: Send GET request to get post with id=150 (/posts/150).");
        assertEquals(getPostsRequestById150().toString(), getTestData().getEmpty(), "Response body is not empty");

        getLogger().info("Step 4: Send POST request to create post with userId=1 and random body and random title (/posts).");
        PostsModel bodyPostResponse = postPostsRequest();
        PostsModel objectFromRandomPostData = getObjectFromJson(PATH_TO_RANDOM_POST_DATA, PostsModel.class);
        softAssert.assertNotNull(String.valueOf(bodyPostResponse.getId()), "ID is not present in response.");
        softAssert.assertEquals(bodyPostResponse.getUserId(), objectFromRandomPostData.getUserId(), "User ID from POST request and response are not equals");
        softAssert.assertEquals(bodyPostResponse.getTitle(), objectFromRandomPostData.getTitle(), "Titles from POST request and response are not equals");
        softAssert.assertEquals(bodyPostResponse.getBody(), objectFromRandomPostData.getBody(), "Bodies from POST request and response are not equals");
        softAssert.assertAll("Post information and response data are not equals");

        getLogger().info("Step 5: Send GET request to get users (/users).");
        List<UsersModel> usersFromRequestList = getAllUsers();
        UsersModel userData = getObjectFromJson(PATH_TO_USER_DATA, UsersModel.class);

        writeListToJson(usersFromRequestList, Path.of(PATH_TO_DYNAMIC_DATA));
        UsersModel user5FromRequest = usersFromRequestList.get((getTestData().getId5()) - 1);
        softAssert.assertEquals(user5FromRequest.getId(), userData.getId(), "ID's are not equal");
        softAssert.assertEquals(user5FromRequest.getName(), userData.getName(), "Names are not equal");
        softAssert.assertEquals(user5FromRequest.getUsername(), userData.getUsername(), "User names are not equal");
        softAssert.assertEquals(user5FromRequest.getEmail(), userData.getEmail(), "Emails are not equal");
        softAssert.assertEquals(user5FromRequest.getAddress().getStreet(), userData.getAddress().getStreet(), "Streets are not equal");
        softAssert.assertEquals(user5FromRequest.getAddress().getSuite(), userData.getAddress().getSuite(), "Suites are not equal");
        softAssert.assertEquals(user5FromRequest.getAddress().getCity(), userData.getAddress().getCity(), "Cities are not equal");
        softAssert.assertEquals(user5FromRequest.getAddress().getZipcode(), userData.getAddress().getZipcode(), "Zipcodes are not equal");
        softAssert.assertEquals(user5FromRequest.getAddress().getGeo().getLat(), userData.getAddress().getGeo().getLat(), "Latitudes are not equal");
        softAssert.assertEquals(user5FromRequest.getAddress().getGeo().getLng(), userData.getAddress().getGeo().getLng(), "Longitudes are not equal");
        softAssert.assertEquals(user5FromRequest.getPhone(), userData.getPhone(), "Phones are not equal");
        softAssert.assertEquals(user5FromRequest.getWebsite(), userData.getWebsite(), "Websites are not equal");
        softAssert.assertEquals(user5FromRequest.getCompany().getName(), userData.getCompany().getName(), "Company names are not equal");
        softAssert.assertEquals(user5FromRequest.getCompany().getCatchPhrase(), userData.getCompany().getCatchPhrase(), "Catch phrases are not equal");
        softAssert.assertEquals(user5FromRequest.getCompany().getBs(), userData.getCompany().getBs(), "Bs are not equal");
        softAssert.assertAll("Some values are not equal");

        getLogger().info("Step 6: Send GET request to get user with id=5 (/users/5).");
        UsersModel objectFromGetRequest = getUserRequestById(getTestData().getId5());
        List<UsersModel> userDataFromPreviousStep = readListFromJson(PATH_TO_DYNAMIC_DATA, UsersModel.class);
        UsersModel user5FromPreviousStep = userDataFromPreviousStep.get((getTestData().getId5()) - 1);
        softAssert.assertEquals(objectFromGetRequest.getId(), user5FromPreviousStep.getId(), "ID's are not equal");
        softAssert.assertEquals(objectFromGetRequest.getName(), user5FromPreviousStep.getName(), "Names are not equal");
        softAssert.assertEquals(objectFromGetRequest.getEmail(), user5FromPreviousStep.getEmail(), "Emails are not equal");
        softAssert.assertEquals(objectFromGetRequest.getAddress().getStreet(), user5FromPreviousStep.getAddress().getStreet(), "Streets are not equal");
        softAssert.assertEquals(objectFromGetRequest.getUsername(), user5FromPreviousStep.getUsername(), "User names are not equal");
        softAssert.assertEquals(objectFromGetRequest.getAddress().getSuite(), user5FromPreviousStep.getAddress().getSuite(), "Suites are not equal");
        softAssert.assertEquals(objectFromGetRequest.getAddress().getCity(), user5FromPreviousStep.getAddress().getCity(), "Cities are not equal");
        softAssert.assertEquals(objectFromGetRequest.getAddress().getZipcode(), user5FromPreviousStep.getAddress().getZipcode(), "Zipcodes are not equal");
        softAssert.assertEquals(objectFromGetRequest.getAddress().getGeo().getLat(), user5FromPreviousStep.getAddress().getGeo().getLat(), "Latitudes are not equal");
        softAssert.assertEquals(objectFromGetRequest.getAddress().getGeo().getLng(), user5FromPreviousStep.getAddress().getGeo().getLng(), "Longitudes are not equal");
        softAssert.assertEquals(objectFromGetRequest.getPhone(), user5FromPreviousStep.getPhone(), "Phones are not equal");
        softAssert.assertEquals(objectFromGetRequest.getWebsite(), user5FromPreviousStep.getWebsite(), "Websites are not equal");
        softAssert.assertEquals(objectFromGetRequest.getCompany().getName(), user5FromPreviousStep.getCompany().getName(), "Company names are not equal");
        softAssert.assertEquals(objectFromGetRequest.getCompany().getCatchPhrase(), user5FromPreviousStep.getCompany().getCatchPhrase(), "Catch phrases are not equal");
        softAssert.assertEquals(objectFromGetRequest.getCompany().getBs(), user5FromPreviousStep.getCompany().getBs(), "Bs are not equal");
        softAssert.assertAll("Some values are not equal");
    }
}