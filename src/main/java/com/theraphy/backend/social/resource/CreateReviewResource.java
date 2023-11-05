package com.theraphy.backend.social.resource;

import com.theraphy.backend.profile.resource.PatientResource;
import com.theraphy.backend.profile.resource.PhysiotherapistResource;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewResource {

    private Long stars;

    @NotBlank
    @NotNull
    @Size(max = 500)
    private String description;
    private PatientResource patient;
    private PhysiotherapistResource physiotherapist;
}
