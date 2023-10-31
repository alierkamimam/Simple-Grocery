package stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hooks.BaseClass;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.Assert;
import pojo.response.GetProductResponse;

public class GetProduct extends BaseClass {
    ObjectMapper responseMapper = new ObjectMapper();
    GetProductResponse responseProduct;

    @Given("Retrieves the product detail with the given {int}, then the response status code should be {int}")
    public void retrieves_the_product(Integer productId, Integer expectedStatusCode) throws JsonProcessingException {

        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/products/" + productId)
                .build();

        response = RestAssured.given()
                .spec(spec)
                .when()
                .get();

        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);

        responseProduct = responseMapper.readValue(response.asString(), GetProductResponse.class);

        /* create a test product object */
        GetProductResponse testProduct= new GetProductResponse();
        testProduct.setId(4643);
        testProduct.setCategory("coffee");
        testProduct.setName("Starbucks Coffee Variety Pack, 100% Arabica");
        testProduct.setManufacturer("Starbucks");
        testProduct.setPrice(40.91);
        testProduct.setCurrentStock(14);
        testProduct.setInStock(true);

        /* compare the test product object with the response product object */
        Assert.assertEquals(testProduct.getId(), responseProduct.getId());
        Assert.assertEquals(testProduct.getCategory(), responseProduct.getCategory());
        Assert.assertEquals(testProduct.getName(), responseProduct.getName());
        Assert.assertEquals(testProduct.getManufacturer(), responseProduct.getManufacturer());
        Assert.assertEquals(testProduct.getPrice(), responseProduct.getPrice());
        Assert.assertEquals(testProduct.getCurrentStock(), responseProduct.getCurrentStock());
        Assert.assertEquals(testProduct.getInStock(), responseProduct.getInStock());

    }
}