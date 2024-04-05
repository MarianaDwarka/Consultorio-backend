package com.marianadwarka.backend.service.impl;

import com.marianadwarka.backend.model.Specialty;
import com.marianadwarka.backend.repo.IGenericRepo;
import com.marianadwarka.backend.repo.ISpecialtyRepo;
import com.marianadwarka.backend.service.ISpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends CRUDImpl<Specialty, Integer> implements ISpecialtyService {

    //@Autowired
    private final ISpecialtyRepo repo;// = new SpecialtyRepo();


    @Override
    protected IGenericRepo<Specialty, Integer> getRepo() {
        return repo;
    }
}
