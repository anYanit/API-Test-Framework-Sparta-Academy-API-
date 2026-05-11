package com.sparta.steps;

import com.sparta.pojos.LoginRequest;
import com.sparta.utils.ApiBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;


public class UserLoginSteps {

    private Response response;
    private LoginRequest loginRequest;

    private final String username = "sparta";
    private final String password = "global";

    @Given("a user exists with a valid username and password")
    public void aUserExistsWithAValidUsernameAndPassword() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
    }

    @When("the user sends a POST request to {string} with valid credentials")
    public void theUserSendsAPOSTRequestToWithValidCredentials(String api) {
        response = RestAssured
                .given()
                .spec(ApiBuilder.authentificationLogin())
                .body(loginRequest)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the API should return a {int} OK response")
    public void theAPIShouldReturnAOKResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain an access token")
    public void theResponseBodyShouldContainAnAccessToken() {
        MatcherAssert.assertThat(response.getBody(), Matchers.notNullValue());
    }
}
