Feature: Create a New Spartan via /api/Spartans API

  @Happy
  Scenario: Successfully create a new Spartan
    Given the user has valid Spartan details
    When the user sends a POST request to Spartan api with the Spartan details
    Then the API should return a 201 Created response
    And the response body should contain the created Spartan details

  @Sad
  Scenario: Fail to create a Spartan with missing required fields
    Given the user provides incomplete Spartan details without firstName
    When the user sends a POST request to Spartan api
    Then the POST API should return a 400 Bad Request response
    And the response body should contain a validation error message

  @Sad
  Scenario: Unauthorized request without Bearer token
    Given the user is not authenticated and wants to create a new Spartan
    When the user sends a POST request to Spartans api without an Authorization header
    Then the POST API should return a 401 Unauthorized response