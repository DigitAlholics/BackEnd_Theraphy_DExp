package com.theraphy.backend.treatments.resource;

import com.theraphy.backend.profile.resource.PhysiotherapistResource;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentResource {
    private Long id;
    private String title;
    private String description;
    private String photoUrl;
    private int sessionsQuantity;
    private PhysiotherapistResource physiotherapist;

}