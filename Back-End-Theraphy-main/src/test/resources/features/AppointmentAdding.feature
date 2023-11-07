Feature: Appointment Adding
  As a Developer
  I want to add Appointments through API
  So that It can be available to applications.

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/appointments" is available for appointments

  @appointment-adding
  Scenario: Add Appointment
    When A Post Request is sent with values 1,1, "Lima", "2022-12-01"
    Then A Response is received with Status 201 by appointment post
    And An Appointment Resource is included in Response Body, with values 1,1, "Lima", "2022-12-01"

  @appointment-adding
  Scenario: Add Appointment
    Given An Appointment Resource with values  1, 1, "2022-12-01", is already stored
    When A Post Request is sent with values 1,1, "Lima", "2022-12-01"
    Then A Response is received with Status 400 by appointment post
    And A Message is included in Response Body, with values "Not all constraints satisfied for Appointment: An Appointment with the same Date and members already exists."