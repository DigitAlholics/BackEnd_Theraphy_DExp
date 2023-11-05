package com.theraphy.backend.test.step;

import com.theraphy.backend.profile.resource.PatientResource;
import com.theraphy.backend.profile.resource.PhysiotherapistResource;
import com.theraphy.backend.social.resource.CreateReviewResource;
import com.theraphy.backend.social.resource.ReviewResource;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentStepDefinitions {
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endpointPath;

    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available")
    public void theEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
        testRestTemplate = new TestRestTemplate();
    }

    @When("A Post Request is sent with values {long},{string}, {patient}, {physiotherapist}")
    public void aPostRequestIsSentWithValues(Long stars, String description, PatientResource patient, PhysiotherapistResource physiotherapist) {
        CreateReviewResource resource = new CreateReviewResource()
                .withStars(stars)
                .withDescription(description)
                .withPatient(patient)
                .withPhysiotherapist(physiotherapist);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateReviewResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);

    }

    @And("A Review Resource is included in Response Body, with values {long},{string}, {patient}, {physiotherapist}")
    public void aReviewResourceIsIncludedInResponseBodyWithValues(Long stars, String description, PatientResource patient, PhysiotherapistResource physiotherapist) {
        ReviewResource expectedResource = new ReviewResource()
                .withStars(stars)
                .withDescription(description)
                .withPatient(patient)
                .withPhysiotherapist(physiotherapist);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        ReviewResource actualResource;
        try {
            actualResource = mapper.readValue(value, ReviewResource.class);

        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new ReviewResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison().isEqualTo(actualResource);
    }

    @Given("A Review Resource with values {string},{string}, {string}, {int}, {PhysiotherapistResource}, is already stored")
    public void aReviewResourceWithValuesIsAlreadyStored(Long stars, String description, PatientResource patient, PhysiotherapistResource physiotherapist) {
        CreateReviewResource resource = new CreateReviewResource()
                .withStars(stars)
                .withDescription(description)
                .withPatient(patient)
                .withPhysiotherapist(physiotherapist);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateReviewResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

}
