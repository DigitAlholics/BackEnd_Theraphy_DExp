package com.theraphy.backend.treatments.domain.persistence;

import com.theraphy.backend.profile.domain.model.entity.Patient;
import com.theraphy.backend.treatments.domain.model.entity.Treatment;
import com.theraphy.backend.treatments.domain.model.entity.TreatmentPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatmentPatientRepository extends JpaRepository<TreatmentPatient,Long>  {

    TreatmentPatient findByPatientAndPatientId(Patient patient, Long patientId);
    Optional<TreatmentPatient> findByTreatmentId(Long treatmentId);
    TreatmentPatient findByTreatmentAndTreatmentTitle(Treatment treatment, String treatmentTitle);


}
