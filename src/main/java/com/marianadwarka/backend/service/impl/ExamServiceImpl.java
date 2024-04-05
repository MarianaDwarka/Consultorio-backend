package com.marianadwarka.backend.service.impl;


import com.marianadwarka.backend.model.Exam;
import com.marianadwarka.backend.repo.IExamRepo;
import com.marianadwarka.backend.repo.IGenericRepo;
import com.marianadwarka.backend.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam, Integer> implements IExamService {

    //@Autowired
    private final IExamRepo repo;// = new ExamRepo();


    @Override
    protected IGenericRepo<Exam, Integer> getRepo() {
        return repo;
    }
}