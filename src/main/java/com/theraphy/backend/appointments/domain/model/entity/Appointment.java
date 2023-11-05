package com.theraphy.backend.appointments.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theraphy.backend.profile.domain.model.entity.Patient;
import com.theraphy.backend.profile.domain.model.entity.Physiotherapist;
import com.theraphy.backend.shared.domain.model.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 20)
    @Column(name = "scheduled_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String scheduledDate;

    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(unique = true)
    private String topic;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String diagnosis;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String done;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "physiotherapist_id", nullable = false)
    private Physiotherapist physiotherapist;
}