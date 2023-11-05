package com.theraphy.backend.profile.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.shared.domain.model.AuditModel;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import com.theraphy.backend.social.domain.model.entity.Review;
import com.theraphy.backend.treatments.domain.model.entity.TreatmentPatient;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @NotBlank
    @Size(max = 60)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 60)
    @Column(name = "last_name")
    private String lastName;

    private int age;

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
    fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    public Patient addAppointment(String scheduledDate,String topic, String diagnosis, String done){
        // Initialize if null
        if(appointments == null) {
            appointments = new HashSet<>();
        }

        // Validate Criterion Name uniqueness for Skill
        if(!appointments.isEmpty()) {
            if (appointments.stream().anyMatch(appointment -> appointment.getScheduledDate().equals(scheduledDate)))
                throw new ResourceValidationException("Criterion", "A criterion with the same name already exists");
        }

        // Add Criterion to Skill
        appointments.add(new Appointment()
                .withScheduledDate(scheduledDate)
                .withTopic(topic)
                .withDiagnosis(diagnosis)
                .withDone(done)
                .withPatient(this));

        return this;
    }


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<Review> reviews = new HashSet<>();
    public Patient addReview(String description, Long stars){
        // Initialize if null
        if(reviews == null) {
            reviews = new HashSet<>();
        }

        // Add Criterion to Skill
        reviews.add(new Review()
                .withStars(stars)
                .withDescription(description)
                .withPatient(this));

        return this;
    }

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<TreatmentPatient> treatments = new HashSet<>();

    public Patient addTreatmentPatient(Date registrationDate, Double progress){
        // Initialize if null
        if(treatments == null) {
            treatments = new HashSet<>();
        }

        // Add Criterion to Skill
        treatments.add(new TreatmentPatient()
                        .withProgress(progress)
                        .withRegistrationDate(registrationDate)
                .withPatient(this));

        return this;
    }




    @NotNull
    @Column(name = "appointment_quantity")
    private Long appointmentQuantity;


    @NotNull
    @NotBlank
    @Size(max = 60)
    private String email;
}
