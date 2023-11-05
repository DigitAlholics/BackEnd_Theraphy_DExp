Feature: User Adding
  As a Developer
  I want to add User through API
  So that It can be available to applications.

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/users" is available

  @user-adding
  Scenario: Add User
    When A Post Request is sent with values "jhon@email.com", "jhon12"
    Then A Response is received with Status 200
    And An User Resource is included in Response Body, with values "jhon@email.com", "jhon12"

  @user-duplicated
  Scenario: Add User with existing Email
    Given An User Resource with values "jhon@email.com", "jhon12" is already stored
    When A Post Request is sent with values "jhon@email.com", "jhon12"
    Then A Response is received with Status 400
    And A Message is included in Response Body, with values "Not all constraints satisfied for User: A User with the same email already exists."