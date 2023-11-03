package test;


import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.appointments.domain.persistence.AppointmentRepository;
import com.theraphy.backend.appointments.service.AppointmentServiceImpl;
import com.theraphy.backend.profile.domain.model.entity.Patient;
import com.theraphy.backend.profile.domain.model.entity.Physiotherapist;
import com.theraphy.backend.profile.domain.persistence.PatientRepository;
import com.theraphy.backend.profile.domain.persistence.PhysiotherapistRepository;

import com.theraphy.backend.profile.service.PatientServiceImpl;
import com.theraphy.backend.profile.service.PhysiotherapistServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@SpringBootTest
public class TestCreatedAppointment {


    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private Appointment appointment;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private Physiotherapist physiotherapist;
    @Autowired
    private PatientServiceImpl patientService;
    @Autowired
    private PhysiotherapistServiceImpl physiotherapistService;
    @Autowired
    private Patient patient;
    private Validator validator;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PhysiotherapistRepository physiotherapistRepository;
    private Appointment retrievedAppointment;

    @Test
    public void testAppointmentLifecycle( ) {

        // Crear un paciente
        patient = new Patient();
        //patient = new Patient(null, null, "Jhon", "Jhons", 30, "", "2011-05-05", null, null, null, null, "aremy@gmail.com" );
        patient.setUserId(1L);
        patient.setFirstName("John");
        patient.setLastName("Jhons");
        patient.setAge(30);
        patient.setPhotoUrl("https://cdn-icons-png.flaticon.com/512/5873/5873284.png");
        patient.setBirthdayDate("2001-28-08");
        patient.setEmail("johnjhons@example.com");
        patient.setAppointments(null);
        patient.setTreatments(null);
        patient.setReviews(null);
        patient.setAppointmentQuantity(1L);

        patientRepository = new PatientRepository() {
            @Override
            public Patient findByFirstName(String firstName) {
                return null;
            }

            @Override
            public Optional<Patient> findByUserId(Long userId) {
                return Optional.empty();
            }

            @Override
            public List<Patient> findAll() {
                return null;
            }

            @Override
            public List<Patient> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Patient> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Patient> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Patient> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Patient> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Patient> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Patient getOne(Long aLong) {
                return null;
            }

            @Override
            public Patient getById(Long aLong) {
                return null;
            }

            @Override
            public Patient getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Patient> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Patient> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Patient> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Patient> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Patient> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Patient entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Patient> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Patient> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Patient> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Patient> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Patient> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Patient, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        patientService = new PatientServiceImpl(patientRepository, validator);
        patient = patientService.create(patient);

        // Crear un fisioterapeuta
        physiotherapist = new Physiotherapist();
        physiotherapist.setAppointments(null);
        physiotherapist.setTreatments(null);
        physiotherapist.setConsultationsQuantity(1L);
        physiotherapist.setBirthdayDate("1995-05-08");
        physiotherapist.setPhotoUrl("https://cdn-icons-png.flaticon.com/512/5873/5873284.png");
        physiotherapist.setFirstName("Nikolas");
        physiotherapist.setUserId(1L);
        physiotherapist.setMaternalSurname("Gomez");
        physiotherapist.setPaternalSurname("Alvarado");
        physiotherapist.setLocation("Surco");
        physiotherapist.setAge(45);
        physiotherapist.setSpecialization("Orthopedics");
        physiotherapist.setEmail("drsmith@example.com");


        physiotherapistRepository  = new PhysiotherapistRepository() {
            @Override
            public Physiotherapist findByFirstName(String firstName) {
                return null;
            }

            @Override
            public Optional<Physiotherapist> findByUserId(Long userId) {
                return Optional.empty();
            }

            @Override
            public List<Physiotherapist> findAll() {
                return null;
            }

            @Override
            public List<Physiotherapist> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Physiotherapist> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Physiotherapist> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Physiotherapist> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Physiotherapist> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Physiotherapist> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Physiotherapist getOne(Long aLong) {
                return null;
            }

            @Override
            public Physiotherapist getById(Long aLong) {
                return null;
            }

            @Override
            public Physiotherapist getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Physiotherapist> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Physiotherapist> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Physiotherapist> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Physiotherapist> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Physiotherapist> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Physiotherapist entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Physiotherapist> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Physiotherapist> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Physiotherapist> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Physiotherapist> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Physiotherapist> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Physiotherapist, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
        physiotherapistService = new PhysiotherapistServiceImpl(physiotherapistRepository, validator);
        physiotherapist = physiotherapistService.create(physiotherapist);

        // Crear una cita
        appointment = new Appointment();
        appointment.setScheduledDate("2023-09-10");
        appointment.setTopic("Back Pain");
        appointment.setDiagnosis("Muscle strain");
        appointment.setDone("No");
        appointment.setPatient(patient);
        appointment.setPhysiotherapist(physiotherapist);

        appointmentRepository = new AppointmentRepository() {
            @Override
            public Optional<Appointment> findByTopicAndPatientId(String topic, Long patientId) {
                return Optional.empty();
            }

            @Override
            public Appointment findByTopic(String topic) {
                return null;
            }

            @Override
            public Optional<Appointment> findByTopicAndPhysiotherapistId(String topic, Long physiotherapistId) {
                return Optional.empty();
            }

            @Override
            public List<Appointment> findAll() {
                return null;
            }

            @Override
            public List<Appointment> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Appointment> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Appointment> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Appointment> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Appointment> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Appointment> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Appointment getOne(Long aLong) {
                return null;
            }

            @Override
            public Appointment getById(Long aLong) {
                return null;
            }

            @Override
            public Appointment getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Appointment> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Appointment> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Appointment> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Appointment> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Appointment> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Appointment entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Appointment> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Appointment> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Appointment> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Appointment> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Appointment> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Appointment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
        appointmentService = new AppointmentServiceImpl(appointmentRepository, validator);
        // Crear la cita
        appointment = appointmentService.create(appointment);


    }


}
