package com.sparta.steps;

import com.sparta.utils.ApiBuilder;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class SearchAllSpartansSteps {

    private Response response;
    private String bearerToken;

    @Given("the user is authenticated")
    public void theUserIsAuthenticated() {
        bearerToken = ApiBuilder.getBearerToken();

        System.out.println(bearerToken);
    }

    @When("the user sends a GET request to Spartans api for all Spartans")
    public void theUserSendsAGETRequestToForAllSpartans() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getAllSpartans(bearerToken))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the API request should return a {int} OK response")
    public void theAPIRequestShouldReturnAOKResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain a list of Spartans")
    public void theResponseBodyShouldContainAListOfSpartans() {
        MatcherAssert.assertThat(response.jsonPath().getList("").size(), Matchers.notNullValue());
    }

    @Given("the user is not authenticated")
    public void theUserIsNotAuthenticated() {
        bearerToken = null;
    }

    @When("the user sends a GET request to Spartans api without an Authorization header")
    public void theUserSendsAGETRequestToWithoutAnAuthorizationHeader() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getAllSpartans(bearerToken))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the Spartan API should return a {int} Unauthorized response")
    public void theSpartanAPIShouldReturnAUnauthorizedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }
}
