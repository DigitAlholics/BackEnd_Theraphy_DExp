package com.theraphy.backend.social.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("reviewsMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public ReviewMapper reviewMapper() { return new ReviewMapper();}
}
