Feature: Retrieve a Specific Course via /api/Courses/{id} API

  @Happy
  Scenario: Successfully retrieve a specific Course
    Given a Course exists with ID 1
    When the user sends a GET request with ID to "/api/Courses"
    Then the Courses with ID API should return a 200 OK response
    And the response body should contain the Course details