package com.marianadwarka.backend.repo;

import com.marianadwarka.backend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository no es necesario
public interface IPatientRepo extends IGenericRepo<Patient, Integer> {
    //Patient save(Patient patient);
}
