package com.theraphy.backend.profile.resource;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientResource {

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String lastName;

    @Min(18)
    private int age;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String photoUrl;

    @NotNull
    @NotBlank
    private String birthdayDate;

    @NotNull
    @Column(name = "appointment_quantity")
    private Long appointmentQuantity;


    @NotNull
    @NotBlank
    @Size(max = 60)
    private String email;

    @NotNull
    private Long userId;
}