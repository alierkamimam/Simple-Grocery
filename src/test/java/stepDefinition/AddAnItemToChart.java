package stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hooks.BaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.Assert;
import pojo.request.AddItemRequest;
import pojo.response.GetCartItems;

import java.util.ArrayList;
import java.util.List;

import static hooks.Hooks.info;

public class AddAnItemToChart extends BaseClass {

    static ObjectMapper responseMapper = new ObjectMapper();
    String cartId;
    Integer itemId;
    List<GetCartItems> getCartItemsResponse;

    List<Integer> productList = new ArrayList<>();

    @Given("Add an item to chart with {int}, then verify status code {int}")
    public Integer addAnItemToChartWithProductIdThenVerifyStatusCode(Integer productId, Integer expectedStatusCode) {

        CreateCart createCart = new CreateCart();
        cartId = createCart.creates_a_cart_for_the_products_client_wants_to_buy_then_verify_status_code(201);

        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(productId);

        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/carts/" + cartId + "/items")
                .build();

        response = RestAssured.given()
                .spec(spec)
                .body(addItemRequest)
                .contentType("application/json")
                .when()
                .post();


        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);

        itemId = response.jsonPath().getInt("itemId");
        info("itemId" + itemId);
        productList.add(productId);

        return itemId;
    }

    @Given("get added cart items ,then verify status code {int}")
    public void getAddedCartItemsThenVerifyStatusCode(int expectedStatusCode) throws JsonProcessingException {

        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/carts/" + cartId + "/items")
                .build();

        response = RestAssured.given()
                .spec(spec)
                .when()
                .get();

        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);

        getCartItemsResponse = responseMapper.readValue(
                response.asString(), new TypeReference<>() {
                }
        );

        getCartItemsResponse.forEach(t ->{

            Assert.assertEquals(t.getProductId(),productList.get(getCartItemsResponse.indexOf(t)));
        });

    }

    @And("Delete an item in the cart, then verify status code {int}")
    public void deleteAnItemInTheCartThenVerifyStatusCode(int expectedStatusCode) {

        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/carts/" + cartId + "/items/" + itemId)
                .build();

        response = RestAssured.given()
                .spec(spec)
                .when()
                .delete();

        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);

    }
}
