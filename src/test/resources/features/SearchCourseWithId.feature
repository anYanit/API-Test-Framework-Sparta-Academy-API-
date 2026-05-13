Feature: Retrieve a Specific Course via /api/Courses/{id} API

  @Happy
  Scenario: Successfully retrieve a specific Course
    Given a Course exists with ID 1
    When the user sends a GET request with ID to "/api/Courses"
    Then the Courses with ID API should return a 200 OK response
    And the response body should contain the Course details

  @Sad
  Scenario: Attempt to retrieve a non-existent Course
    Given no Course exists with id 9999
    When the user sends a GET request with ID 9999 to "/api/Courses"
    Then the API should return a 404 Not Found response
    And the response body for the request should contain the title "Not Found"