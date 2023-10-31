package stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hooks.BaseClass;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.Assert;
import pojo.response.GetAllProductsResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GetAllProducts extends BaseClass {
    static ObjectMapper responseMapper = new ObjectMapper();
    static ObjectMapper testMapper = new ObjectMapper();
    List<GetAllProductsResponse> getAllProductsResponse;
    List<GetAllProductsResponse> testGetAllProductsResponse;

    @Given("returns a list of products from the inventory, then the status code should be {int}")
    public void get_all_products_then_verify_status_code(Integer expectedStatusCode) throws IOException {

        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/products")
                .build();

        response = RestAssured.given()
                .spec(spec)
                .when().
                get();

        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);

        /* new TypeReference<List<GetAllProductsResponse>>() {} returns JSON data to specific
         * is a usage in the Jackson library used to convert to a type.
         * This statement uses a feature of the Jackson library to specify JSON data.
         * Creates a type reference to convert to a type.
         */


        /* convert response JSON to POJO */
        getAllProductsResponse = responseMapper.readValue(
                response.asString(),
                new TypeReference<List<GetAllProductsResponse>>() {}
        );

        /* convert test data JSON to POJO */
        testGetAllProductsResponse = testMapper.readValue(
                new File("src/test/resources/TestData/getAllProducts.json"),
                new TypeReference<List<GetAllProductsResponse>>() {}
        );

        /* compare response and test data */
        getAllProductsResponse.forEach(t -> {

                    Assert.assertEquals(
                            t.getId(),
                            testGetAllProductsResponse.get(getAllProductsResponse.indexOf(t)).getId()
                    );
                    Assert.assertEquals(
                            t.getCategory(),
                            testGetAllProductsResponse.get(getAllProductsResponse.indexOf(t)).getCategory()
                    );
                    Assert.assertEquals(
                            t.getName(),
                            testGetAllProductsResponse.get(getAllProductsResponse.indexOf(t)).getName()
                    );
                    Assert.assertEquals(
                            t.getInStock(),
                            testGetAllProductsResponse.get(getAllProductsResponse.indexOf(t)).getInStock()
                    );
                }
        );
    }
}