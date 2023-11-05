package com.theraphy.backend.treatments.resource;

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
public class CreateTreatmentResource {

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String title;

    @NotBlank
    @NotNull
    @Size(max = 500)
    private String description;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String photoUrl;

    private int sessionsQuantity;

    private PhysiotherapistResource physiotherapist;

}