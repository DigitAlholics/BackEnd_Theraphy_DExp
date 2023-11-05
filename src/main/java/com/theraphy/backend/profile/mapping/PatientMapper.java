package com.theraphy.backend.profile.mapping;

import com.theraphy.backend.profile.domain.model.entity.Patient;
import com.theraphy.backend.profile.resource.CreatePatientResource;
import com.theraphy.backend.profile.resource.PatientResource;
import com.theraphy.backend.profile.resource.UpdatePatientResource;
import com.theraphy.backend.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class PatientMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public PatientResource toResource(Patient model) {
        return mapper.map(model, PatientResource.class);
    }

    public Patient toModel(CreatePatientResource resource) {
        return mapper.map(resource, Patient.class);
    }

    public Patient toModel(UpdatePatientResource resource) { return mapper.map(resource, Patient.class);}

    public Page<PatientResource> modelListPage(List<Patient> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, PatientResource.class), pageable, modelList.size());
    }



}