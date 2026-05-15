package com.sparta.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.pojos.Spartan;
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

import java.io.File;
import java.io.IOException;

public class UpdateSpartanSteps {
    private Response response;
    private String bearerToken;

    private ObjectMapper mapper;
    private File file;
    private Spartan newSpartan;
    private Spartan updatedSpartan;

    private String spartanId;

    public Spartan newSpartanFromExternalFile(String path) throws IOException {
        file = new File(path);
        mapper = new ObjectMapper();
        return mapper.readValue(file, Spartan.class);
    }

    @Given("a Spartan is created with the id {string}")
    public void aSpartanIsCreatedWithTheId(String id) throws IOException {
        spartanId = id;

        newSpartan = newSpartanFromExternalFile("src/test/resources/external/NewSpartan.json");
        newSpartan.setId(Integer.parseInt(spartanId));

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

        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, spartanId))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(response.jsonPath().getString("id"), Matchers.is(id));
    }

    @And("the user provides updated Spartan details")
    public void theUserProvidesUpdatedSpartanDetails() throws IOException {
        updatedSpartan = newSpartanFromExternalFile("src/test/resources/external/UpdatedSpartan.json");
        updatedSpartan.setId(Integer.parseInt(spartanId));
    }

    @When("the user sends a PUT request to Spartan api with the updated details")
    public void theUserSendsAPUTRequestToSpartanApiWithTheUpdatedDetails() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, String.valueOf(spartanId)))
                .body(updatedSpartan)
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the PUT API should return a {int} Undocumented response")
    public void thePUTAPIShouldReturnAOKResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the Spartan details should be Updated")
    public void theSpartanDetailsShouldBeUpdated() {
        String expectedFirstname = updatedSpartan.getFirstName();
        String expectedLastname = updatedSpartan.getLastName();

        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, spartanId))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(response.jsonPath().getString("firstName"), Matchers.is(expectedFirstname));
        MatcherAssert.assertThat(response.jsonPath().getString("lastName"), Matchers.is(expectedLastname));


        // delete the newly created and updated Spartan to allow re-running of test
        response = RestAssured
                .given()
                .spec(ApiBuilder.deleteSpartan(bearerToken, String.valueOf(spartanId)))
                .when()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    @Given("no Spartan exists with id of {string}")
    public void noSpartanExistsWithIdOf(String id) {
        spartanId = id;
    }

    @And("the user provides valid Spartan details")
    public void theUserProvidesValidSpartanDetails() throws IOException {
        updatedSpartan = newSpartanFromExternalFile("src/test/resources/external/UpdatedSpartan.json");
        updatedSpartan.setId(Integer.parseInt(spartanId));
    }

    @When("the user sends a PUT request to Spartan api")
    public void theUserSendsAPUTRequestToSpartanApi() {
        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, String.valueOf(spartanId)))
                .body(updatedSpartan)
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the PUT API should return a {int} Not Found response")
    public void thePUTAPIShouldReturnANotFoundResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @Given("the user is not authenticated and wants to update a Spartan")
    public void theUserIsNotAuthenticatedAndWantsToUpdateASpartan() {
        bearerToken = null;
    }

    @When("the user sends a PUT request to Spartan api without an Authorization header")
    public void theUserSendsAPUTRequestToSpartanApiWithoutAnAuthorizationHeader() throws IOException {
        updatedSpartan = newSpartanFromExternalFile("src/test/resources/external/UpdatedSpartan.json");
        updatedSpartan.setId(1);

        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, String.valueOf(1)))
                .body(updatedSpartan)
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the PUT API should return a {int} Unauthorized response")
    public void thePUTAPIShouldReturnAUnauthorizedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @Given("a Spartan already exists with id {string}")
    public void aSpartanAlreadyExistsWithId(String id) {
        spartanId = id;

        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, spartanId))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(response.jsonPath().getString("id"), Matchers.is(String.valueOf(spartanId)));
    }

    @And("the user provides incomplete Spartan details without a lastName")
    public void theUserProvidesIncompleteSpartanDetailsWithoutALastName() throws IOException {
        updatedSpartan = newSpartanFromExternalFile("src/test/resources/external/UpdatedSpartan.json");
        updatedSpartan.setId(Integer.parseInt(spartanId));
        updatedSpartan.setLastName(null);
    }

    @When("the user sends a PUT request to Spartan api without a lastName")
    public void theUserSendsAPUTRequestToSpartanApiWithoutALastName() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, String.valueOf(spartanId)))
                .body(updatedSpartan)
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the PUT API should return a {int} Bad Request response")
    public void thePUTAPIShouldReturnABadRequestResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the PUT response body should contain a validation error message")
    public void thePUTResponseBodyShouldContainAValidationErrorMessage() {
        String errorMessage = "The LastName field is required.";
        MatcherAssert.assertThat(response.jsonPath().getString("errors.LastName"), Matchers.containsString(errorMessage));
    }
}
