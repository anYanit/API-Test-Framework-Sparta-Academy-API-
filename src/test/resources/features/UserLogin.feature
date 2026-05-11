Feature: User Login via /Auth/login API

  @Happy
  Scenario: Successful login with valid credentials
    Given a user exists with a valid username and password
    When the user sends a POST request to "/Auth/login" with valid credentials
    Then the API should return a 200 OK response
    And the response body should contain an access token