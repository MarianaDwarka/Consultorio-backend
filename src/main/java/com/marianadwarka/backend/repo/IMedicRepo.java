package com.marianadwarka.backend.repo;

import com.marianadwarka.backend.model.Medic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMedicRepo extends IGenericRepo<Medic, Integer> {
    //Medic save(Medic Medic);
}
