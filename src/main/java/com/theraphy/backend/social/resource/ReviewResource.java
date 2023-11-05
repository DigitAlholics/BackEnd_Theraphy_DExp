package com.theraphy.backend.social.resource;

import com.theraphy.backend.profile.resource.PatientResource;
import com.theraphy.backend.profile.resource.PhysiotherapistResource;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResource {
    private Long id;
    private String description;
    private Long stars;
    private PatientResource patient;
    private PhysiotherapistResource physiotherapist;
}
