package com.sparta.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.pojos.Spartan;
import com.sparta.utils.ApiBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.File;
import java.io.IOException;

public class CreateNewSpartanSteps {

    private Response response;
    private String bearerToken;

    private ObjectMapper mapper;
    private File file;
    private Spartan newSpartan;

    public Spartan newSpartanFromExternalFile(String path) throws IOException {
        file = new File(path);
        mapper = new ObjectMapper();
        return mapper.readValue(file, Spartan.class);
    }

    @Given("the user has valid Spartan details")
    public void theUserHasValidSpartanDetails() throws IOException {
        newSpartan = newSpartanFromExternalFile("src/test/resources/external/NewSpartan.json");
    }

    @When("the user sends a POST request to Spartan api with the Spartan details")
    public void theUserSendsAPOSTRequestToSpartanApiWithTheSpartanDetails() {
        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.createNewSpartan(bearerToken))
                .body(newSpartan)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the API should return a {int} Created response")
    public void theAPIShouldReturnACreatedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain the created Spartan details")
    public void theResponseBodyShouldContainTheCreatedSpartanDetails() {
        MatcherAssert.assertThat(response.jsonPath().getString("firstName"), Matchers.is(newSpartan.getFirstName()));
    }

    @Given("the user provides incomplete Spartan details without firstName")
    public void theUserProvidesIncompleteSpartanDetailsWithoutFirstName() throws IOException {
        newSpartan = newSpartanFromExternalFile("src/test/resources/external/MissingFirstNameSpartan.json");
    }

    @When("the user sends a POST request to Spartan api")
    public void theUserSendsAPOSTRequestToSpartanApi() {
        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.createNewSpartan(bearerToken))
                .body(newSpartan)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the POST API should return a {int} Bad Request response")
    public void thePOSTAPIShouldReturnABadRequestResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain a validation error message")
    public void theResponseBodyShouldContainAValidationErrorMessage() {
        String errorMessage = "The FirstName field is required.";
        MatcherAssert.assertThat(response.jsonPath().getString("errors.FirstName"), Matchers.containsString(errorMessage));
    }

    @Given("the user is not authenticated and wants to create a new Spartan")
    public void theUserIsNotAuthenticatedAndWantsToCreateANewSpartan() throws IOException {
        bearerToken = null;
        newSpartan = newSpartanFromExternalFile("src/test/resources/external/NewSpartan.json");
    }

    @When("the user sends a POST request to Spartans api without an Authorization header")
    public void theUserSendsAPOSTRequestToSpartansApiWithoutAnAuthorizationHeader()  {
        response = RestAssured
                .given()
                .spec(ApiBuilder.createNewSpartan(bearerToken))
                .body(newSpartan)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the POST API should return a {int} Unauthorized response")
    public void thePOSTAPIShouldReturnAUnauthorizedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }
}
