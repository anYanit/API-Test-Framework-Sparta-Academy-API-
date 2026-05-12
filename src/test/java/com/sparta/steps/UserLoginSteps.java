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

    @Given("a user exists with a valid username and password {string} and {string}")
    public void aUserExistsWithAValidUsernameAndPassword(String username, String password) {
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

    @Given("the user does not exist in the system")
    public void theUserDoesNotExistInTheSystem() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("random");
        loginRequest.setPassword("random");
    }

    @When("the user sends a POST request to {string} with unknown credentials")
    public void theUserSendsAPOSTRequestToWithUnknownCredentials(String api) {
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

    @Then("the API should return a {int} Unauthorized response")
    public void theAPIShouldReturnAUnauthorizedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain the title {string}")
    public void theResponseBodyShouldContainTheTitle(String expectedTitle) {
        MatcherAssert.assertThat(response.jsonPath().getString("title"), Matchers.is(expectedTitle));
    }

    @When("the user sends a POST request to {string} without a username and password")
    public void theUserSendsAPOSTRequestToWithoutAUsernameAndPassword(String api) {
        loginRequest = new LoginRequest();
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

    @Then("the API should return a {int} Bad Request response")
    public void theAPIShouldReturnABadRequestResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain a validation error message for no username")
    public void theResponseBodyShouldContainAValidationErrorMessageForNoUsername() {
        String noUsernameMsg = "The Username field is required.";
        MatcherAssert.assertThat(response.jsonPath().getString("errors.Username"), Matchers.containsString(noUsernameMsg));
    }

    @And("the response body should contain a validation error message for no password")
    public void theResponseBodyShouldContainAValidationErrorMessageForNoPassword() {
        String noPasswordMsg = "The Password field is required.";
        MatcherAssert.assertThat(response.jsonPath().getString("errors.Password"), Matchers.containsString(noPasswordMsg));
    }
}
