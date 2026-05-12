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

public class SearchAllCoursesSteps {

    private Response response;

    @Given("Courses exist in the system")
    public void courses_exist_in_the_system() {

    }

    @When("the user sends a GET request to {string}")
    public void theUserSendsAGETRequestTo(String api) {
        response = RestAssured
                .given()
                .spec(ApiBuilder.getAllCourses())
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the Courses API should return a {int} OK response")
    public void theCoursesAPIShouldReturnAOKResponse(int expectedResponse) {
        int responseCode = response.getStatusCode();
        MatcherAssert.assertThat(responseCode, Matchers.is(expectedResponse));
    }

    @And("the response body should contain a list of Courses")
    public void theResponseBodyShouldContainAListOfCourses() {
        MatcherAssert.assertThat(response.jsonPath().getList("").size(), Matchers.notNullValue());
    }
}
