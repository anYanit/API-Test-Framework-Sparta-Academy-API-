Feature: Retrieve a Specific Course via /api/Courses/{id} API

  @Happy
  Scenario Outline: Successfully retrieve a specific Course
    Given a Course exists with ID "<id>"
    When the user sends a GET request with ID to Courses api
    Then the Courses with ID API should return a 200 OK response
    And the response body should contain the Course details: id, "<name>" and "<stream>"

    Examples:
      | id | name | stream |
      | 1   | TECH 300   | C# Dev |
      | 2   | TECH 301  | Java Dev |
      | 3   | TECH 302   | C# Test |


  @Sad
  Scenario: Attempt to retrieve a non-existent Course
    Given no Course exists with id "9999"
    When the user sends a GET request with ID "9999" to Courses api
    Then the API should return a 404 Not Found response
    And the response body for the request should contain the title "Not Found"