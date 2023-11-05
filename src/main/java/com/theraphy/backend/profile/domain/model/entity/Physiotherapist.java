package com.theraphy.backend.profile.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.shared.domain.model.AuditModel;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import com.theraphy.backend.social.domain.model.entity.Review;
import com.theraphy.backend.treatments.domain.model.entity.Treatment;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "physiotherapists")
public class Physiotherapist extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 60)
    @Column(name = "first_name")
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

    private int age;

    @NotNull
    @NotBlank
    private String location;

    @NotNull
    @NotBlank
    @Size(max = 300)
    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull
    @NotBlank
    @Column(name = "birthday_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String birthdayDate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "physiotherapist")
    private Set<Appointment> appointments = new HashSet<>();

    public Physiotherapist addAppointment(String scheduledDate, String topic, String diagnosis, String done) {
        // Initialize if null
        if (appointments == null) {
            appointments = new HashSet<>();
        }

        // Validate Criterion Name uniqueness for Skill
        if (!appointments.isEmpty()) {
            if (appointments.stream().anyMatch(appointment -> appointment.getScheduledDate().equals(scheduledDate)))
                throw new ResourceValidationException("Physiotherapist", "A physiotherapist with the same name already exists");
        }

        // Add Criterion to Skill
        appointments.add(new Appointment()
                .withScheduledDate(scheduledDate)
                .withTopic(topic)
                .withDiagnosis(diagnosis)
                .withDone(done)
                .withPhysiotherapist(this));

        return this;
    }

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "physiotherapist")
    private Set<Review> reviews = new HashSet<>();
    public Physiotherapist addReview(String description, Long stars){
        // Initialize if null
        if(reviews == null) {
            reviews = new HashSet<>();
        }

        // Add Criterion to Skill
        reviews.add(new Review()
                .withStars(stars)
                .withDescription(description)
                .withPhysiotherapist(this));

        return this;
    }

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "physiotherapist")
    private Set<Treatment> treatments = new HashSet<>();

    public Physiotherapist addTreatment(String title, String description, String photoUrl, int sessionQuantity){
        // Initialize if null
        if(treatments == null) {
            treatments = new HashSet<>();
        }

        // Add Criterion to Skill
        treatments.add(new Treatment()
                        .withPhotoUrl(photoUrl)
                        .withTitle(title)
                        .withDescription(description)
                        .withSessionsQuantity(sessionQuantity)
                .withPhysiotherapist(this));

        return this;
    }

    @NotNull
    private Long userId;

    @NotNull
    @Column(name = "consultations_quantity")
    private Long consultationsQuantity;


    @NotNull
    @NotBlank
    @Size(max = 60)
    private String email;


    public Double rating;


}