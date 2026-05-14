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

public class DeleteSpartanSteps {

    private Response response;
    private String bearerToken;

    private ObjectMapper mapper;
    private File file;
    private Spartan newSpartan;
    private String nonexistentId;

    public Spartan newSpartanFromExternalFile(String path) throws IOException {
        file = new File(path);
        mapper = new ObjectMapper();
        return mapper.readValue(file, Spartan.class);
    }

    @Given("a Spartan with id {string} is created")
    public void aSpartanWithIdIsCreated(String id) throws IOException {
        newSpartan = newSpartanFromExternalFile("src/test/resources/external/NewSpartan.json");
        newSpartan.setId(Integer.parseInt(id));

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
                .spec(ApiBuilder.getSpartanWithId(bearerToken, id))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(response.jsonPath().getString("id"), Matchers.is(id));
    }

    @When("the user sends a DELETE request to Spartan api with the ID")
    public void theUserSendsADELETERequestToSpartanApiWithTheID() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.deleteSpartan(bearerToken, String.valueOf(newSpartan.getId())))
                .when()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the API should return a {int} Undocumented response")
    public void theAPIShouldReturnAUndocumentedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the Spartan ID should not be searchable in the system")
    public void theSpartanIDShouldNotBeSearchableInTheSystem() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getSpartanWithId(bearerToken, String.valueOf(newSpartan.getId())))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(204));
    }

    @Given("no Spartan exists with the id {string}")
    public void noSpartanExistsWithTheId(String id) {
        nonexistentId = id;
    }

    @When("the user sends a DELETE request to Spartan api")
    public void theUserSendsADELETERequestToSpartanApi() {
        bearerToken = ApiBuilder.getBearerToken();
        response = RestAssured
                .given()
                .spec(ApiBuilder.deleteSpartan(bearerToken, nonexistentId))
                .when()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the DELETE API should return a {int} Not Found response")
    public void theDELETEAPIShouldReturnANotFoundResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @Given("the user is not authenticated and wants to delete a Spartan")
    public void theUserIsNotAuthenticatedAndWantsToDeleteASpartan() {
        bearerToken = null;
    }

    @When("the user sends a DELETE request to Spartan without an Authorization header")
    public void theUserSendsADELETERequestToSpartanWithoutAnAuthorizationHeader() {
        response = RestAssured
                .given()
                .spec(ApiBuilder.deleteSpartan(bearerToken, "1"))
                .when()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the DELETE API should return a {int} Unauthorized response")
    public void theDELETEAPIShouldReturnAUnauthorizedResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }
}
