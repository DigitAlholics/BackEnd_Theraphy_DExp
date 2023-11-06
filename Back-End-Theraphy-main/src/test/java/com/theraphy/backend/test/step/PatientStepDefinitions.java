package com.theraphy.backend.test.step;

import com.theraphy.backend.profile.resource.CreatePatientResource;
import com.theraphy.backend.profile.resource.PatientResource;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

//@CucumberContextConfiguration
public class PatientStepDefinitions {

    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endpointPath;

    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available for patients")
    public void theEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
        testRestTemplate = new TestRestTemplate();
    }

    @Then("A Response is received with Status {int} for patient")
    public void aResponseIsReceivedWithStatus(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }
    @When("A Post Request is sent with values {int},{string}, {string}, {int}, {string}, {string}, {string}, {int}")
    public void aPostRequestIsSentWithValues(int userId, String firstName, String lastName, int age, String photoUrl, String birthdate, String email, int appointment) {
        Long usId = (long) userId;
        Long consult = (long) appointment;
        CreatePatientResource resource = new CreatePatientResource()
                .withUserId(usId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAge(age)
                .withPhotoUrl(photoUrl)
                .withBirthdayDate(birthdate)
                .withEmail(email)
                .withAppointmentQuantity(consult);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreatePatientResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);

    }

    @And("An Patient Resource is included in Response Body, with values {int},{string}, {string}, {int}, {string}, {string}, {string}, {int}")
    public void anPatientResourceIsIncludedInResponseBodyWithValues(int userId, String firstName, String lastName, int age, String photoUrl, String birthdate, String email, int appointment) {
        Long usId = (long) userId;
        Long consult = (long) appointment;
        PatientResource expectedResource = new PatientResource()
                .withUserId(usId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAge(age)
                .withPhotoUrl(photoUrl)
                .withBirthdayDate(birthdate)
                .withEmail(email)
                .withAppointmentQuantity(consult);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        PatientResource actualResource;
        try {
            actualResource = mapper.readValue(value, PatientResource.class);

        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new PatientResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison().isEqualTo(actualResource);
    }

    @Given("An Patient Resource with values {int},{string}, {string}, {int}, {string}, {string}, is already stored")
    public void anPatientResourceWithValuesIsAlreadyStored(int userId, String firstName, String lastName, int age, String photoUrl, String birthdate) {
        CreatePatientResource resource = new CreatePatientResource()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAge(age)
                .withPhotoUrl(photoUrl)
                .withBirthdayDate(birthdate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreatePatientResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }



}
