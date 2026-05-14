Feature: Retrieve a Specific Spartan via /api/Spartans/{id} API

  @Happy
  Scenario Outline: Successfully retrieve a specific Spartan
    Given a Spartan exists with id "<id>"
    When the user sends a GET request to Spartans api with ID
    Then the Spartan API should return a 200 OK response
    And the response body should contain the Spartan details: id, "<firstName>" and "<lastName>"

    Examples:
      | id | firstName | lastName |
      | 1   | Sparty   | McFly |
      | 2   | John  | Lennon |
      | 3   | Paul   | McCartney |

  @Sad
  Scenario: Attempt to retrieve a non-existent Spartan
    Given no Spartan exists with id "9999"
    When the user sends a GET request with invalid ID to Spartans api
    Then the Spartan API should return a 404 Not Found response
    And the response body for the Spartan api request should contain the title "Not Found"


  Scenario: Unauthorized request without Bearer token
    Given the user is not authenticated and wants to search for a Spartan
    When the user sends a GET request to Spartans api with ID without an Authorization header
    Then the API response should return a 401 Unauthorized response