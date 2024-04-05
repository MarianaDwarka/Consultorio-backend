package com.marianadwarka.backend.controller;

import com.marianadwarka.backend.dto.PatientDTO;
import com.marianadwarka.backend.model.Patient;
import com.marianadwarka.backend.service.IPatientService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
//@RequestMapping("${patient.controller.path}")
@RequiredArgsConstructor
public class PatientController {


    //@Autowired
    private final IPatientService service;// = new PatientService();
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll(){
        //List<PatientDTO> list = service.findAll().stream().map(e -> modelMapper.map(e, PatientDTO.class)).toList();
        //List<PatientDTO> list = service.findAll().stream().map(e -> this.convertToDto(e)).toList();
        List<PatientDTO> list = service.findAll().stream().map(this::convertToDto).toList(); //programación funcional

        return ResponseEntity.ok(list);
    }

    /*@GetMapping
    public ResponseEntity<List<Patient>> findAll(){
        List<Patient> list = service.findAll();
        return ResponseEntity.ok(list);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id){
        Patient obj = service.findById(id);

        // return ResponseEntity.ok(modelMapper.map(obj, PatientDTO.class));
        return ResponseEntity.ok(convertToDto(obj));
        //return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity <Patient> findById(@PathVariable("id") Integer id){
        Patient obj = service.findById(id);
        return ResponseEntity.ok(obj);
    }*/

    /*@PostMapping
    public ResponseEntity<Patient> save(@RequestBody Patient patient){
        Patient obj = service.save(patient);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
        return ResponseEntity.created(location).build();
    }*/

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PatientDTO dto){
        //Patient obj = service.save(modelMapper.map(dto, Patient.class));
        Patient obj = service.save(convertToEntity(dto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PatientDTO dto){
        dto.setIdPatient(id);
        //Patient obj = service.update(id, modelMapper.map(dto, Patient.class));
        Patient obj = service.update(id, convertToEntity(dto));

        //return ResponseEntity.ok(modelMapper.map(dto, Patient.class));
        return ResponseEntity.ok(convertToDto(obj));
    }

    /*@PutMapping ("/{id}")
    public ResponseEntity<Patient> update(@PathVariable("id") Integer id, @RequestBody Patient patient){
        patient.setIdPatient(id);
        Patient obj = service.update(id, patient);
        return ResponseEntity.ok(obj);
    }*/

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //204 No content
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<PatientDTO> resource = EntityModel.of(convertToDto(service.findById(id)));

        //generar un link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("patient-info-byId"));
        resource.add(link2.withRel("patient-all-info"));

        return resource;
    }

    private PatientDTO convertToDto(Patient obj){
        return modelMapper.map(obj, PatientDTO.class);
    }

    private Patient convertToEntity(PatientDTO dto){
        return modelMapper.map(dto, Patient.class);
    }

    /*@GetMapping
    public Patient save(){
        Patient patient = new Patient();
        patient.setIdPatient(0);
        patient.setFirstName("Mariana");
        patient.setEmail("mariana@aven.com.mx");
        return service.validAndSave(patient);
    }*/
}
