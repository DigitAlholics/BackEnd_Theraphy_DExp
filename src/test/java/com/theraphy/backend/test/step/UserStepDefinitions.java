package com.theraphy.backend.test.step;

import com.theraphy.backend.security.resource.CreateUserResource;
import com.theraphy.backend.security.resource.UserResource;
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

@CucumberContextConfiguration
public class UserStepDefinitions {
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

    @When("A Post Request is sent with values {string}, {string}")
    public void aPostRequestIsSentWithValues(String email, String password) {
        CreateUserResource resource = new CreateUserResource()
                .withEmail(email)
                .withPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

    @Then("A Response is received with Status {int}")
    public void aResponseIsReceivedWithStatus(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @And("An User Resource is included in Response Body, with values {string}, {string}")
    public void anUserResourceIsIncludedInResponseBodyWithValues(String email, String password) {
        UserResource expectedResource = new UserResource()
                .withEmail(email)
                .withPassword(password);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        UserResource actualResource;
        try {
            actualResource = mapper.readValue(value, UserResource.class);

        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new UserResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison().isEqualTo(actualResource);
    }

    @Given("An User Resource with values {string}, {string} is already stored")
    public void anUserResourceWithValuesIsAlreadyStored(String email, String password) {
        CreateUserResource resource = new CreateUserResource()
                .withEmail(email)
                .withPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);

    }

    @And("A Message is included in Response Body, with values {string}")
    public void aMessageIsIncludedInResponseBodyWithValues(String expectedMessage) {
        String responseBody = responseEntity.getBody();
        assertThat(responseBody).contains(expectedMessage);
    }


}
