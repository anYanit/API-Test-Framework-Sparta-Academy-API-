Feature: Retrieve All Courses via /api/Courses API

  @Happy
  Scenario: Successfully retrieve all Courses
    Given Courses exist in the system
    When the user sends a GET request to "/api/Courses"
    Then the Courses API should return a 200 OK response
    And the response body should contain a list of Courses