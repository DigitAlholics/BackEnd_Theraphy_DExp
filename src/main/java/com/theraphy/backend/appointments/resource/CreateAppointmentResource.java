package com.theraphy.backend.appointments.resource;

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
public class CreateAppointmentResource {

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String scheduledDate;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String topic;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String diagnosis;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String done;

    private PatientResource patient;
    private PhysiotherapistResource physiotherapist;
}