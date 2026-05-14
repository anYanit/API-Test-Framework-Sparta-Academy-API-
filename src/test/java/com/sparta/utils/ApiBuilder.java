package com.sparta.utils;

import com.sparta.pojos.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.ResourceBundle;

public class ApiBuilder {

    public static final ResourceBundle resource = ResourceBundle.getBundle("config");

    // base URI
    public static final String BASE_URI = resource.getString("automation.base_url");

    // POST endpoints
    public static final String POST_LOGIN = resource.getString("automation.post_login");
    public static final String POST_SPARTANS = resource.getString("automation.post_spartans");

    // GET endpoints
    public static final String GET_COURSES = resource.getString("automation.get_courses");
    public static final String GET_SPARTANS = resource.getString("automation.get_spartans");

    private static RequestSpecBuilder getBaseSpecBuilder(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(path)
                .setContentType("application/json")
                .addHeaders(Map.of(
                        "Accept", "*/*"
                ));
    }

    public static RequestSpecification authentificationLogin() {
        return getBaseSpecBuilder(POST_LOGIN)
                .build();
    }

    public static RequestSpecification getAllCourses() {
        return getBaseSpecBuilder(GET_COURSES)
                .build();
    }

    public static RequestSpecification getCourseWithId(String id) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(GET_COURSES + "/" + id)
                .setContentType("application/json")
                .addHeaders(Map.of(
                        "Accept", "*/*"
                ))
                .build();
    }

    public static String getBearerToken() {
        Response response;
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername("sparta");
        loginRequest.setPassword("global");

        response = RestAssured
                .given()
                .spec(ApiBuilder.authentificationLogin())
                .body(loginRequest)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        return response.jsonPath().getString("token");
    }

    public static RequestSpecification getAllSpartans(String bearerToken) {
        return getBaseSpecBuilder(GET_SPARTANS)
                .addHeaders(Map.of(
                        "Authorization", "Bearer " + bearerToken
                ))
                .build();
    }

    public static RequestSpecification getSpartanWithId(String bearerToken, String id) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(GET_SPARTANS + "/" + id)
                .setContentType("application/json")
                .addHeaders(Map.of(
                        "Accept", "*/*",
                        "Authorization", "Bearer " + bearerToken
                ))
                .build();
    }

    public static RequestSpecification createNewSpartan(String bearerToken) {
        return getBaseSpecBuilder(POST_SPARTANS)
                .addHeaders(Map.of(
                        "Authorization", "Bearer " + bearerToken
                ))
                .build();
    }
}
