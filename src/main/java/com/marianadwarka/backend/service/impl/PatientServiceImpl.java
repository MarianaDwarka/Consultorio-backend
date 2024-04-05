package com.marianadwarka.backend.service.impl;

import com.marianadwarka.backend.exception.ModelNotFoundException;
import com.marianadwarka.backend.model.Patient;
import com.marianadwarka.backend.repo.IGenericRepo;
import com.marianadwarka.backend.repo.IPatientRepo;
import com.marianadwarka.backend.service.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

    //@Autowired
    private final IPatientRepo repo; // = new MedicRepo();

    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }

    /*@Override
    public Patient save(Patient patient) {
        return repo.save(patient);
    }

    @Override
    public Patient update(Integer id, Patient patient) {
        //Validar ID
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        return repo.save(patient);
    }

    @Override
    public List<Patient> findAll() {
        return repo.findAll();
    }

    @Override
    public Patient findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(Integer id) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        repo.deleteById(id);

    }*/

    /*@Override
    public Patient validAndSave(Patient patient){
        if(patient.getIdPatient() == 0){
            return repo.save(patient);
        }else{
            return new Patient();
        }
    }*/
}
