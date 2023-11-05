package com.theraphy.backend.treatments.resource;

import com.theraphy.backend.profile.resource.PatientResource;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateTreatmentPatientResource {

    private TreatmentResource treatment;

    private PatientResource patient;

    @NotNull
    @NotBlank
    private Date registrationDate;

    private Double progress;
}
