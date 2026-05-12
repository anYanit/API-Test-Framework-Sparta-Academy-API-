package com.sparta.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.ResourceBundle;

public class ApiBuilder {

    public static final ResourceBundle resource = ResourceBundle.getBundle("config");

    // base URI
    public static final String BASE_URI = resource.getString("automation.base_url");

    // POST endpoints
    public static final String POST_LOGIN = resource.getString("automation.post_login");

    // GET endpoints
    public static final String GET_ALL_COURSES = resource.getString("automation.get_courses");

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
        return getBaseSpecBuilder(GET_ALL_COURSES)
                .build();
    }
}
