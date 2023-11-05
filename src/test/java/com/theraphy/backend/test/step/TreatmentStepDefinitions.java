package com.theraphy.backend.test.step;

import com.theraphy.backend.profile.resource.PhysiotherapistResource;
import com.theraphy.backend.treatments.resource.CreateTreatmentResource;
import com.theraphy.backend.treatments.resource.TreatmentResource;
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

public class TreatmentStepDefinitions {
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

    @When("A Post Request is sent with values {string},{string}, {string}, {int}, {PhysiotherapistResource}")
    public void aPostRequestIsSentWithValues(String title, String description, String photoUrL, int sessionsQuantity, PhysiotherapistResource physiotherapist) {
        CreateTreatmentResource resource = new CreateTreatmentResource()
                .withTitle(title)
                .withDescription(description)
                .withPhotoUrl(photoUrL)
                .withSessionsQuantity(sessionsQuantity)
                .withPhysiotherapist(physiotherapist);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTreatmentResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);

    }

    @And("A Treatment Resource is included in Response Body, with values {string},{string}, {string}, {int}, {PhysiotherapistResource}")
    public void aTreatmentResourceIsIncludedInResponseBodyWithValues(String title, String description, String photoUrL, int sessionsQuantity, PhysiotherapistResource physiotherapist) {
        TreatmentResource expectedResource = new TreatmentResource()
                .withTitle(title)
                .withDescription(description)
                .withPhotoUrl(photoUrL)
                .withSessionsQuantity(sessionsQuantity)
                .withPhysiotherapist(physiotherapist);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        TreatmentResource actualResource;
        try {
            actualResource = mapper.readValue(value, TreatmentResource.class);

        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new TreatmentResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison().isEqualTo(actualResource);
    }

    @Given("A Treatment Resource with values {string},{string}, {string}, {int}, {PhysiotherapistResource}, is already stored")
    public void aPatientResourceWithValuesIsAlreadyStored(String title, String description, String photoUrL, int sessionsQuantity, PhysiotherapistResource physiotherapist) {
        CreateTreatmentResource resource = new CreateTreatmentResource()
                .withTitle(title)
                .withDescription(description)
                .withPhotoUrl(photoUrL)
                .withSessionsQuantity(sessionsQuantity)
                .withPhysiotherapist(physiotherapist);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTreatmentResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }
}
