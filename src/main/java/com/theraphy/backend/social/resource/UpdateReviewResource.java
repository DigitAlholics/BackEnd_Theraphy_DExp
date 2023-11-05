package com.theraphy.backend.social.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateReviewResource {
    private Long stars;

    @NotBlank
    @NotNull
    @Size(max = 500)
    private String description;
}
