Feature: Retrieve All Spartans via /api/Spartans API

  @Happy
  Scenario: Successfully retrieve all Spartans
    Given the user is authenticated
    When the user sends a GET request to Spartans api for all Spartans
    Then the API request should return a 200 OK response
    And the response body should contain a list of Spartans

  @Sad
  Scenario: Unauthorized request without Bearer token
    Given the user is not authenticated
    When the user sends a GET request to Spartans api without an Authorization header
    Then the Spartan API should return a 401 Unauthorized response