package com.theraphy.backend.test.step;

import com.theraphy.backend.appointments.resource.AppointmentResource;
import com.theraphy.backend.appointments.resource.CreateAppointmentResource;
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

public class ReviewStepDefinitions {
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

    @When("A Post Request is sent with values {string},{string}, {string}, {string}")
    public void aPostRequestIsSentWithValues(String scheduledDate, String topic, String diagnosis, String done) {
        CreateAppointmentResource resource = new CreateAppointmentResource()
                .withScheduledDate(scheduledDate)
                .withTopic(topic)
                .withDiagnosis(diagnosis)
                .withDone(done);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateAppointmentResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);

    }

    @And("An Appointment Resource is included in Response Body, with values {string},{string}, {string}, {string}")
    public void anAppointmentResourceIsIncludedInResponseBodyWithValues(String scheduledDate, String topic, String diagnosis, String done) {
        AppointmentResource expectedResource = new AppointmentResource()
                .withScheduledDate(scheduledDate)
                .withTopic(topic)
                .withDiagnosis(diagnosis)
                .withDone(done);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        AppointmentResource actualResource;
        try {
            actualResource = mapper.readValue(value, AppointmentResource.class);

        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new AppointmentResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison().isEqualTo(actualResource);
    }

    @Given("An Appointment Resource with values {string},{string}, {string}, {int}, {PhysiotherapistResource}, is already stored")
    public void anAppointmentResourceWithValuesIsAlreadyStored(String scheduledDate, String topic, String diagnosis, String done) {
        CreateAppointmentResource resource = new CreateAppointmentResource()
                .withScheduledDate(scheduledDate)
                .withTopic(topic)
                .withDiagnosis(diagnosis)
                .withDone(done);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateAppointmentResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }
}
