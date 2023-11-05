package com.theraphy.backend.treatments.mapping;

import com.theraphy.backend.shared.mapping.EnhancedModelMapper;
import com.theraphy.backend.treatments.domain.model.entity.TreatmentPatient;
import com.theraphy.backend.treatments.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class TreatmentPatientMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public TreatmentPatientResource toResource(TreatmentPatient model) {
        return mapper.map(model, TreatmentPatientResource.class);
    }

    public TreatmentPatient toModel(CreateTreatmentPatientResource resource) {
        return mapper.map(resource, TreatmentPatient.class);
    }

    public TreatmentPatient toModel(UpdateTreatmentPatientResource resource) {return mapper.map(resource, TreatmentPatient.class);}

    public Page<TreatmentPatientResource> modelListPage(List<TreatmentPatient> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, TreatmentPatientResource.class), pageable, modelList.size());
    }
}
