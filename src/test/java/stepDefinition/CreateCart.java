package stepDefinition;

import hooks.BaseClass;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import static hooks.Hooks.info;

public class CreateCart extends BaseClass {

    @Given("creates a cart for the products client wants to buy, then verify status code {int}")
    public String creates_a_cart_for_the_products_client_wants_to_buy_then_verify_status_code(Integer expectedStatusCode) {

        spec= new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/carts")
                .build();

        response = RestAssured.given()
                .spec(spec)
                .when()
                .post();

        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);

        String cartId = response.jsonPath().getString("cartId");
        info("cartId: " + cartId);

        return cartId;
    }


}