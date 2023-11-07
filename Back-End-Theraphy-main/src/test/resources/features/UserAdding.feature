Feature: User Adding
  As a Developer
  I want to add User through API
  So that It can be available to applications.

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/users" is available for user

  @user-adding
  Scenario: Add User
    When A Post Request is sent with values "jhon2@email.com", "jhon122", "patient"
    Then A Response is received with Status 201
    And An User Resource is included in Response Body, with values "jhon2@email.com", "jhon122", "patient"

  @user-duplicated
  Scenario: Add User with existing Email
    Given An User Resource with values "jhon@email.com", "jhon122", "patient" is already stored
    When A Post Request is sent with values "jhon@email.com", "jhon122", "patient"
    Then A Response is received with Status 400
    And A Message is included in Response Body, with values "Not all constraints satisfied for User: A user with the same email exists."