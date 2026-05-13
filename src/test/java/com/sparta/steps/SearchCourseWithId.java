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

public class SearchCourseWithId {

    private Response response;
    private int courseId;

    @Given("a Course exists with ID {int}")
    public void aCourseExistsWithId(int id) {
        courseId = id;
    }

    @When("the user sends a GET request with ID to {string}")
    public void theUserSendsAGETRequestWithIDTo(String api) {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getCourseWithId(courseId))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the Courses with ID API should return a {int} OK response")
    public void theCoursesWithIDAPIShouldReturnAOKResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain the Course details")
    public void theResponseBodyShouldContainTheCourseDetails() {
        MatcherAssert.assertThat(response.jsonPath().getString("id"), Matchers.is(String.valueOf(courseId)));
    }

    @Given("no Course exists with id {int}")
    public void noCourseExistsWithId(int id) {
    }

    @When("the user sends a GET request with ID {int} to {string}")
    public void theUserSendsAGETRequestWithIDTo(int id, String api) {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getCourseWithId(id))
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the API should return a {int} Not Found response")
    public void theAPIShouldReturnANotFoundResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body for the request should contain the title {string}")
    public void theResponseBodyForTheRequestShouldContainTheTitle(String expectedTitle) {
        MatcherAssert.assertThat(response.jsonPath().getString("title"), Matchers.is(expectedTitle));
    }
}
