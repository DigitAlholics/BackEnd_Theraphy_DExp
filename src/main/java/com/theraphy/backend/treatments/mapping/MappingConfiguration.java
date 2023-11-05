package com.theraphy.backend.treatments.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("treatmentsMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public TreatmentMapper treatmentMapper() { return new TreatmentMapper();}

    @Bean
    public TreatmentPatientMapper treatmentPatientMapper() { return new TreatmentPatientMapper();}
}