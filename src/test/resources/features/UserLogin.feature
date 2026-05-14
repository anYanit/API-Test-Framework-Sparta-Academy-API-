Feature: User Login via /Auth/login API

  @Happy
  Scenario Outline: Successful login with valid credentials
    Given a user exists with a valid username and password "<username>" and "<password>"
    When the user sends a POST request to login api with valid credentials
    Then the API should return a 200 OK response
    And the response body should contain an access token

    Examples:
      | username | password |
      | sparta   | global   |

  @Sad
  Scenario: Failed login with non-existent user
    Given the user does not exist in the system
    When the user sends a POST request to login api with unknown credentials
    Then the API should return a 401 Unauthorized response
    And the response body should contain the title "Unauthorized"

  @Edge
  Scenario: Failed login with missing username and password
    When the user sends a POST request to login api without a username and password
    Then the API should return a 400 Bad Request response
    And the response body should contain a validation error message for no username
    And the response body should contain a validation error message for no password