Feature: Delete a Spartan via /api/Spartans/{id} API

  @Happy
  Scenario: Successfully delete an existing Spartan
    Given a Spartan with id "100" is created
    When the user sends a DELETE request to Spartan api with the ID
    Then the API should return a 204 Undocumented response
    And the Spartan ID should not be searchable in the system

  @Sad
  Scenario: Attempt to delete a non-existent Spartan
    Given no Spartan exists with the id "9999"
    When the user sends a DELETE request to Spartan api
    Then the DELETE API should return a 404 Not Found response

  @Sad
  Scenario: Unauthorized request without Bearer token
    Given the user is not authenticated and wants to delete a Spartan
    When the user sends a DELETE request to Spartan without an Authorization header
    Then the DELETE API should return a 401 Unauthorized response