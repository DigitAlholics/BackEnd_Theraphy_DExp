package com.theraphy.backend.profile.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class PatientResource {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private Long appointmentQuantity;
    private String email;
    private int age;
    private String photoUrl;
    private String birthdayDate;
}