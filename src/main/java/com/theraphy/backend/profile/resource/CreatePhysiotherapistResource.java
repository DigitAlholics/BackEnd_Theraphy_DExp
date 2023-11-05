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
public class CreatePhysiotherapistResource {
    @NotNull
    @NotBlank
    @Size(max = 60)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 60)
    @Column(name = "paternal_surname")
    private String paternalSurname;

    @NotNull
    @NotBlank
    @Size(max = 60)
    @Column(name = "maternal_surname")
    private String maternalSurname;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String specialization;

    @Min(18)
    private int age;

    @NotNull
    @NotBlank
    private String location;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String photoUrl;

    @NotNull
    @NotBlank
    private String birthdayDate;

    @NotNull
    @Column(name = "consultations_quantity")
    private Long consultationsQuantity;


    @NotNull
    @NotBlank
    @Size(max = 60)
    private String email;


    public Double rating;

    @NotNull
    private Long userId;
}