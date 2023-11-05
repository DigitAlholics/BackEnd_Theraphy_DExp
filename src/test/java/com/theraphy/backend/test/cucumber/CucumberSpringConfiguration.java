package com.theraphy.backend.test.cucumber;


import com.theraphy.backend.BackendTheraphyApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
@CucumberContextConfiguration
@SpringBootTest(classes = BackendTheraphyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = BackendTheraphyApplication.class,
        loader = SpringBootContextLoader.class)
public class CucumberSpringConfiguration {
}
