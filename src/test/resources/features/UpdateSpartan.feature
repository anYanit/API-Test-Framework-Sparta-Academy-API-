Feature: Update a Spartan via /api/Spartan/{id} API

  @Happy
  Scenario: Successfully update an existing Spartan
    Given a Spartan is created with the id "99"
    And the user provides updated Spartan details
    When the user sends a PUT request to Spartan api with the updated details
    Then the PUT API should return a 204 Undocumented response
    And the Spartan details should be Updated

  @Sad
  Scenario: Attempt to update a non-existent Spartan
    Given no Spartan exists with id of "9999"
    And the user provides valid Spartan details
    When the user sends a PUT request to Spartan api
    Then the PUT API should return a 404 Not Found response

  @Sad
  Scenario: Unauthorized request without Bearer token
    Given the user is not authenticated and wants to update a Spartan
    When the user sends a PUT request to Spartan api without an Authorization header
    Then the PUT API should return a 401 Unauthorized response

  @Edge
  Scenario: Fail to update a Spartan with missing required fields
    Given a Spartan already exists with id "1"
    And the user provides incomplete Spartan details without a lastName
    When the user sends a PUT request to Spartan api without a lastName
    Then the PUT API should return a 400 Bad Request response
    And the PUT response body should contain a validation error message