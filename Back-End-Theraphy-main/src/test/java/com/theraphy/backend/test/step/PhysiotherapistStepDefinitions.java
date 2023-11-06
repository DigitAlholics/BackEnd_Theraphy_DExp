package com.theraphy.backend.test.step;

import com.theraphy.backend.profile.resource.CreatePhysiotherapistResource;
import com.theraphy.backend.profile.resource.PhysiotherapistResource;
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
public class PhysiotherapistStepDefinitions {

    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endpointPath;

    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available for physiotherapist")
    public void theEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
        testRestTemplate = new TestRestTemplate();
    }

    @When("A Post Request is sent with values {int}, {string}, {string}, {string},{string}, {int}, {string}, {string}, {double}, {string}, {string}, {int}")
    public void aPostRequestIsSentWithValues(int userId, String firstName, String maternal, String paternal, String address, int age, String photoUrl, String birthdate, double rating, String specialization, String email, int consultations) {
        Long usId = (long) userId;
        Long consult = (long) consultations;
        CreatePhysiotherapistResource resource = new CreatePhysiotherapistResource()
                .withUserId(usId)
                .withFirstName(firstName)
                .withPaternalSurname(maternal)
                .withMaternalSurname(paternal)
                .withLocation(address)
                .withAge(age)
                .withPhotoUrl(photoUrl)
                .withBirthdayDate(birthdate)
                .withConsultationsQuantity(consult)
                .withRating(rating)
                .withSpecialization(specialization)
                .withEmail(email);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreatePhysiotherapistResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

    @Then("A Response is received with Status {int} in physio")
    public void aResponseIsReceivedWithStatus(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @And("An Physiotherapist Resource is included in Response Body, with values {int}, {string}, {string}, {string},{string}, {int}, {string}, {string}, {double}, {string}, {string}, {int}")
    public void anPhysiotherapistResourceIsIncludedInResponseBodyWithValues(int userId, String firstName, String maternal, String paternal, String address, int age, String photoUrl, String birthdate, double rating, String specialization, String email, int consultations) {
        Long usId = (long) userId;
        Long consult = (long) consultations;
        PhysiotherapistResource expectedResource = new PhysiotherapistResource()
                .withUserId(usId)
                .withFirstName(firstName)
                .withPaternalSurname(maternal)
                .withMaternalSurname(paternal)
                .withLocation(address)
                .withAge(age)
                .withPhotoUrl(photoUrl)
                .withBirthdayDate(birthdate)
                .withConsultationsQuantity(consult)
                .withRating(rating)
                .withSpecialization(specialization)
                .withEmail(email);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        PhysiotherapistResource actualResource;
        try {
            actualResource = mapper.readValue(value, PhysiotherapistResource.class);

        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new PhysiotherapistResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison().isEqualTo(actualResource);
    }

    @Given("An Physiotherapist Resource with values {int}, {string}, {string}, {string}, {int}, {string}, {string}, is already stored")
    public void anPhysiotherapistResourceWithValuesIsAlreadyStored(int userId, String firstName, String lastName, String location, int age, String photoUrl, String birthdate) {
        CreatePhysiotherapistResource resource = new CreatePhysiotherapistResource()
                .withFirstName(firstName)
                .withLocation(location)
                .withAge(age)
                .withPhotoUrl(photoUrl)
                .withBirthdayDate(birthdate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreatePhysiotherapistResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);

    }

}