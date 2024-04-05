package com.marianadwarka.backend.service.impl;

import com.marianadwarka.backend.exception.ModelNotFoundException;
import com.marianadwarka.backend.model.Medic;
import com.marianadwarka.backend.repo.IGenericRepo;
import com.marianadwarka.backend.repo.IMedicRepo;
import com.marianadwarka.backend.service.IMedicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl extends CRUDImpl<Medic, Integer> implements IMedicService {

    private final IMedicRepo repo;


    @Override
    protected IGenericRepo<Medic, Integer> getRepo() {
        return repo;
    }
}
