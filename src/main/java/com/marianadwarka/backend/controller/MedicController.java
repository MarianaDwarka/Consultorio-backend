package com.marianadwarka.backend.controller;

import com.marianadwarka.backend.dto.MedicDTO;
import com.marianadwarka.backend.model.Medic;
import com.marianadwarka.backend.service.IMedicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/medics")
//@RequestMapping("${medic.controller.path}")
@RequiredArgsConstructor
public class MedicController {


    //@Autowired
    private final IMedicService service;// = new MedicService();
    @Qualifier("medicMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll(){
        //List<MedicDTO> list = service.findAll().stream().map(e -> modelMapper.map(e, MedicDTO.class)).toList();
        //List<MedicDTO> list = service.findAll().stream().map(e -> this.convertToDto(e)).toList();
        List<MedicDTO> list = service.findAll().stream().map(this::convertToDto).toList(); //programación funcional

        return ResponseEntity.ok(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id){
        Medic obj = service.findById(id);

        // return ResponseEntity.ok(modelMapper.map(obj, MedicDTO.class));
        return ResponseEntity.ok(convertToDto(obj));
        //return new ResponseEntity<>(obj, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MedicDTO dto){
        //Medic obj = service.save(modelMapper.map(dto, Medic.class));
        Medic obj = service.save(convertToEntity(dto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody MedicDTO dto){
        dto.setIdMedic(id);
        //Medic obj = service.update(id, modelMapper.map(dto, Medic.class));
        Medic obj = service.update(id, convertToEntity(dto));

        //return ResponseEntity.ok(modelMapper.map(dto, Medic.class));
        return ResponseEntity.ok(convertToDto(obj));
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //204 No content
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<MedicDTO> resource = EntityModel.of(convertToDto(service.findById(id)));

        //generar un link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("medic-info-byId"));
        resource.add(link2.withRel("medic-all-info"));

        return resource;
    }

    private MedicDTO convertToDto(Medic obj){
        return modelMapper.map(obj, MedicDTO.class);
    }

    private Medic convertToEntity(MedicDTO dto){
        return modelMapper.map(dto, Medic.class);
    }


}
