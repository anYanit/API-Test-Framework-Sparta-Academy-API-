package com.sparta.steps;

import com.sparta.utils.ApiBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class SearchCourseWithIdSteps {

    private Response response;
    private String courseId;

    @Given("a Course exists with ID {string}")
    public void aCourseExistsWithId(String id) {
        courseId = id;
    }

    @When("the user sends a GET request with ID to Courses api")
    public void theUserSendsAGETRequestWithIDTo() {
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

    @And("the response body should contain the Course details: id, {string} and {string}")
    public void theResponseBodyShouldContainTheCourseDetails(String name, String stream) {
        MatcherAssert.assertThat(response.jsonPath().getString("id"), Matchers.is(String.valueOf(courseId)));
        MatcherAssert.assertThat(response.jsonPath().getString("name"), Matchers.is(String.valueOf(name)));
        MatcherAssert.assertThat(response.jsonPath().getString("stream"), Matchers.is(String.valueOf(stream)));
    }

    @Given("no Course exists with id {string}")
    public void noCourseExistsWithId(String id) {
    }

    @When("the user sends a GET request with ID {string} to Courses api")
    public void theUserSendsAGETRequestWithIDTo(String id) {
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
