Feature: Patient Adding
  As a Developer
  I want to add Patient through API
  So that It can be available to applications.

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/patients" is available for patients

  @patient-adding
  Scenario: Add Patient
    When A Post Request is sent with values 9,"Jhon", "Doe", 15, "photo.com", "02-01-1997", "jhon2@email.com", 12
    Then A Response is received with Status 201 for patient
    And An Patient Resource is included in Response Body, with values 9,"Jhon", "Doe", 15, "photo.com", "02-01-1997", "jhon2@email.com", 12

  @patient-duplicated
  Scenario: Add Patient with existing UserId
    Given An Patient Resource with values 1,"Jhon", "Doe", 15, "photo.com", "02/01/1997", is already stored
    When A Post Request is sent with values 9,"Jhon", "Doe", 15, "photo.com", "02-01-1997", "jhon2@email.com", 12
    Then A Response is received with Status 400
    And A Message is included in Response Body, with values "Not all constraints satisfied for Patient: A Patient with the same UserId  already exists."