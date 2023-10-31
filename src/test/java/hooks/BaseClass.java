package hooks;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class BaseClass {

    /**
     * This is the base class for BOOKING the test classes
     */
    public static final String BASE_URL = ConfigReader.getProperty("base.url");
    public static final String API_KEY = ConfigReader.getProperty("api.key");
    public static RequestSpecification spec;
    public static Response response;

}
