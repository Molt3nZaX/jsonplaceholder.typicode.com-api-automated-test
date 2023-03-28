package typicode.utils;

import typicode.models.TestDataModel;

public class TestDataUtils implements DataConstantsUtils {
    public static TestDataModel getTestData() {
        return JsonUtils.getObjectFromJson(PATH_TO_TEST_DATA, TestDataModel.class);
    }
}