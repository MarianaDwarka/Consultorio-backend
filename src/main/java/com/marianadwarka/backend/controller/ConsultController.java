package com.marianadwarka.backend.controller;

import com.marianadwarka.backend.dto.ConsultDTO;
import com.marianadwarka.backend.dto.ConsultListExamDTO;
import com.marianadwarka.backend.model.Consult;
import com.marianadwarka.backend.model.Exam;
import com.marianadwarka.backend.service.IConsultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consults")
//@RequestMapping("${consult.controller.path}")
//Inyección de dependencias por constructor
@RequiredArgsConstructor // los atributos que están marcados como finales serán inyección de dependencias
public class ConsultController {

    //@Autowired
    private final IConsultService service;// = new ConsultService();
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    /*public ConsultController(ConsultService service) {
        this.service = service;
    }*/

    @GetMapping
    public ResponseEntity<List<ConsultDTO>> findAll(){
        List<ConsultDTO> list = service.findAll().stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id){
        Consult obj = service.findById(id);

        return ResponseEntity.ok(convertToDto(obj));
        //return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    /*@PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultDTO dto){
        Consult obj = service.save(convertToEntity(dto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();

        return ResponseEntity.created(location).build();
    }*/
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultListExamDTO dto) throws Exception{
        Consult cons = convertToEntity(dto.getConsult());
        //List<Exam> exams = dto.getLstExam().stream().map(e -> modelMapper.map(e, Exam.class)).toList();
        List<Exam> exams = modelMapper.map(dto.getLstExam(), new TypeToken<List<Exam>>(){}.getType());
        Consult obj = service.saveTransactional(cons, exams);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ConsultDTO dto){
        dto.setIdConsult(id);
        Consult obj = service.update(id, convertToEntity(dto));

        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);

        return ResponseEntity.noContent().build(); //204 NO CONTENT
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(service.findById(id)));

        //generar un link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("consult-info-byId"));
        resource.add(link2.withRel("consult-all-info"));

        return resource;
    }

    private ConsultDTO convertToDto(Consult obj){
        return modelMapper.map(obj, ConsultDTO.class);
    }

    private Consult convertToEntity(ConsultDTO dto){
        return modelMapper.map(dto, Consult.class);
    }
}
