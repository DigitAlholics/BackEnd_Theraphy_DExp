package com.theraphy.backend.profile.service;

import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.profile.domain.model.entity.Physiotherapist;
import com.theraphy.backend.profile.domain.persistence.PhysiotherapistRepository;
import com.theraphy.backend.profile.domain.service.PhysiotherapistService;
import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PhysiotherapistServiceImpl implements PhysiotherapistService {
    private static final String ENTITY = "Physiotherapist";

    private final PhysiotherapistRepository physiotherapistRepository;

    private final Validator validator;

    public PhysiotherapistServiceImpl(PhysiotherapistRepository physiotherapistRepository, Validator validator) {
        this.physiotherapistRepository = physiotherapistRepository;
        this.validator = validator;
    }

    @Override
    public List<Physiotherapist> getAll() {
        return physiotherapistRepository.findAll();
    }

    @Override
    public Page<Physiotherapist> getAll(Pageable pageable) {
        return physiotherapistRepository.findAll(pageable);
    }

    @Override
    public Physiotherapist getById(Long physiotherapistId) {
        return physiotherapistRepository.findById(physiotherapistId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, physiotherapistId));
    }

    @Override
    public Physiotherapist getByUserId(Long userId) {
        return physiotherapistRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, userId));
    }


    @Override
    public Physiotherapist create(Physiotherapist physiotherapist) {
        Set<ConstraintViolation<Physiotherapist>> violations = validator.validate(physiotherapist);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Name Uniqueness validation
        Physiotherapist physiotherapistWithFirstName = physiotherapistRepository.findByFirstName(physiotherapist.getFirstName());

        if(physiotherapistWithFirstName != null)
            throw new ResourceValidationException(ENTITY,
                    "A Physiotherapist with the same first name already exists.");

        return physiotherapistRepository.save(physiotherapist);
    }

    @Override
    public Physiotherapist update(Long physiotherapistId, Physiotherapist request) {
        Set<ConstraintViolation<Physiotherapist>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return physiotherapistRepository.findById(physiotherapistId).map( physiotherapist ->
                        physiotherapistRepository.save(
                                physiotherapist.withFirstName(request.getFirstName()).
                                        withUserId(request.getUserId()).
                                        withPaternalSurname(request.getPaternalSurname()).
                                        withMaternalSurname(request.getMaternalSurname()).
                                        withAge(request.getAge()).
                                        withRating(request.getRating()).
                                        withPhotoUrl(request.getPhotoUrl()).
                                        withLocation(request.getLocation()).
                                        withBirthdayDate(request.getBirthdayDate()).
                                        withSpecialization(request.getSpecialization()).
                                        withEmail(request.getEmail()).
                                        withConsultationsQuantity(request.getConsultationsQuantity())
                                ))
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,physiotherapistId));
    }

    @Override
    public ResponseEntity<?> delete(Long patientId) {
        return physiotherapistRepository.findById(patientId).map(physiotherapist -> {
            physiotherapistRepository.delete(physiotherapist);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY,patientId));
    }

    @Override
    public Physiotherapist addAppointmentToPhysiotherapist(Long physiotherapistId, String scheduledDate, String topic, String diagnosis, String done) {
        /*return physiotherapistRepository.findById(physiotherapistId).map(physiotherapist -> {
            return physiotherapistRepository.save(physiotherapist.addAppointment(scheduledDate,topic,diagnosis, done));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,physiotherapistId));*/

        Optional<Physiotherapist> optionalPhysiotherapist = physiotherapistRepository.findById(physiotherapistId);
        if (optionalPhysiotherapist.isEmpty()) {
            throw new ResourceNotFoundException(ENTITY, physiotherapistId);
        }
        Physiotherapist physiotherapist = optionalPhysiotherapist.get();

        // Verificar el límite de citas diarias
        Long currentDailyAppointments = physiotherapist.getConsultationsQuantity();
        Long dailyAppointmentLimit = 6L;

        if (currentDailyAppointments >= dailyAppointmentLimit) {
            throw new ResourceValidationException(ENTITY, "Daily appointment limit exceeded.");
        }
        // Crear la cita
        Appointment appointment = new Appointment();
        appointment.setScheduledDate(scheduledDate);
        appointment.setTopic(topic);
        appointment.setDiagnosis(diagnosis);
        appointment.setDone(done);
        appointment.setPhysiotherapist(physiotherapist);

        // Agregar la cita al conjunto de citas del fisioterapeuta
        physiotherapist.getAppointments().add(appointment);

        // Incrementar el contador de citas diarias
        physiotherapist.setConsultationsQuantity(currentDailyAppointments + 1);

        // Guardar los cambios
        physiotherapistRepository.save(physiotherapist);

        // Devolver la cita recién creada
        return appointment.getPhysiotherapist();
    }

    @Override
    public Physiotherapist addReviewToPhysiotherapist(Long physiotherapistId, String description, Long stars) {
        return physiotherapistRepository.findById(physiotherapistId).map(physiotherapist -> {
            return physiotherapistRepository.save(physiotherapist.addReview(description, stars));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,physiotherapistId));    }

    @Override
    public Physiotherapist addTreatmentToPhysiotherapist(Long physiotherapistId, String title, String description, String photoUrl, int sessionQuantity) {
        return physiotherapistRepository.findById(physiotherapistId).map(patient -> {
            return physiotherapistRepository.save(patient.addTreatment(title, description, photoUrl, sessionQuantity));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,physiotherapistId));    }
}