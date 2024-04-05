package com.marianadwarka.backend.service;

import com.marianadwarka.backend.model.Consult;
import com.marianadwarka.backend.model.Exam;

import java.util.List;

public interface IConsultService extends ICRUD<Consult, Integer> {

    Consult saveTransactional(Consult consult, List<Exam> exam);

}

