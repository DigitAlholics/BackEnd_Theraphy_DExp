package com.theraphy.backend.security.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UserResource {
    private Long id;
    //private String username;
    private String email;
    private String password;
    private String type;
}
