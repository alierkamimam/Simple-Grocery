package stepDefinition;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hooks.BaseClass;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import pojo.request.RegisterClientRequest;
import pojo.response.RegisterClientResponse;

import static hooks.Hooks.info;

public class RegisterClient extends BaseClass {
    static ObjectMapper responseMapper = new ObjectMapper();
    String accessToken;

    @Given("Register a new client with the given {string} and {string}, then verify the response status code is {int}")
    public String register_a_new_client_with_the_given_and(String clientName,
                                                           String clientMail,
                                                           Integer statusCode) throws JsonProcessingException {

        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api-clients")
                .build();

        /* Create a new client with the given name and mail */
        RegisterClientRequest registerClientRequest = new RegisterClientRequest();
        registerClientRequest.setClientName(clientName);
        registerClientRequest.setClientEmail(clientMail);

        /* send the request and get the response */
        response = RestAssured.given()
                .spec(spec)
                .contentType("application/json")
                .body(registerClientRequest)
                .when()
                .post();

        /* Verify the response status code */
        response
                .then()
                .assertThat()
                .statusCode(statusCode);

        RegisterClientResponse registerClientResponse =
                responseMapper.readValue(response.asString(), RegisterClientResponse.class);

        /* If the response status code is 201, then get the access token */
        if (statusCode == 201) {
            accessToken = registerClientResponse.getAccessToken();
            info("accessToken:" + accessToken);
        }

        if (statusCode == 400)
            info("error:" + registerClientResponse.getError());

        if (statusCode == 409)
            info("error:" + registerClientResponse.getError());


        return accessToken;
        }
    }