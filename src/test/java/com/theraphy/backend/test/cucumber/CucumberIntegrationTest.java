package com.theraphy.backend.test.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.theraphy.backendtheraphy.test.step")
public class CucumberIntegrationTest {
}
