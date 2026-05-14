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

public class SearchSpartanWithIdSteps {

    private Response response;
    private String bearerToken;
    private String spartanId;
    private String invalidId;

    @Given("a Spartan exists with id {string}")
    public void aSpartanExistsWithId(String id) {
        spartanId = id;
    }

    @When("the user sends a GET request to Spartans api with ID")
    public void theUserSendsAGETRequestToSpartansApiWithID() {
        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, spartanId))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the Spartan API should return a {int} OK response")
    public void theSpartanAPIShouldReturnAOKResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain the Spartan details: id, {string} and {string}")
    public void theResponseBodyShouldContainTheSpartanDetailsIdAnd(String firstName, String lastName) {
        MatcherAssert.assertThat(response.jsonPath().getString("id"), Matchers.is(String.valueOf(spartanId)));
        MatcherAssert.assertThat(response.jsonPath().getString("firstName"), Matchers.is(String.valueOf(firstName)));
        MatcherAssert.assertThat(response.jsonPath().getString("lastName"), Matchers.is(String.valueOf(lastName)));
    }

    @Given("no Spartan exists with id {string}")
    public void noSpartanExistsWithId(String id) {
        invalidId = id;
    }

    @When("the user sends a GET request with invalid ID to Spartans api")
    public void theUserSendsAGETRequestWithInvalidIDToSpartansApi() {
        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, invalidId))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the Spartan API should return a {int} Not Found response")
    public void theSpartanAPIShouldReturnANotFoundResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body for the Spartan api request should contain the title {string}")
    public void theResponseBodyForTheSpartanApiRequestShouldContainTheTitle(String expectedTitle) {
        MatcherAssert.assertThat(response.jsonPath().getString("title"), Matchers.is(expectedTitle));
    }

    @Given("the user is not authenticated and wants to search for a Spartan")
    public void theUserIsNotAuthenticatedAndWantsToSearchForASpartan() {
        bearerToken = null;
    }

    @When("the user sends a GET request to Spartans api with ID without an Authorization header")
    public void theUserSendsAGETRequestToSpartansApiWithIDWithoutAnAuthorizationHeader() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, "1"))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the API response should return a {int} Unauthorized response")
    public void theAPIResponseShouldReturnAUnauthorizedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }


}
