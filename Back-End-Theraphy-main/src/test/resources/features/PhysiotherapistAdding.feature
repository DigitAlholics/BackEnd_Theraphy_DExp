Feature: Physiotherapist Adding
  As a Developer
  I want to add Physiotherapist through API
  So that It can be available to applications.

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/physiotherapists" is available for physiotherapist

  @physiotherapist-adding
  Scenario: Add Physiotherapist
    When A Post Request is sent with values 7, "Jhon", "Doe", "Duo","New York Street", 15, "photo.com", "02-01-1997", 2.7, "Head", "jhon@email.com", 12
    Then A Response is received with Status 201 in physio
    And An Physiotherapist Resource is included in Response Body, with values 7, "Jhon", "Doe", "Duo","New York Street", 15, "photo.com", "02-01-1997", 2.7, "Head", "jhon@email.com", 12

  @physiotherapist-duplicated
  Scenario: Add Physiotherapist with existing UserId
    Given An Physiotherapist Resource with values 1, "Jhon", "Doe", "New York Street", 15, "photo.com", "02/01/1997", is already stored
    When A Post Request is sent with values 7, "Jhon", "Doe", "Duo","New York Street", 15, "photo.com", "02-01-1997", 2.7, "Head", "jhon@email.com", 12
    Then A Response is received with Status 400
    And A Message is included in Response Body, with values "Not all constraints satisfied for Physiotherapist: A Physiotherapist with the same UserId  already exists."