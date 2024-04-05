package com.marianadwarka.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idConsult;

    @ManyToOne //Referencia a llave foranea
    //Creación de la columna de enlace
    @JoinColumn(name = "id_patient", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_PATIENT")) // en esta tabla se crea la columna id_patient
    private Patient patient;

    @ManyToOne //Referencia a llave foranea
    //Creación de la columna de enlace
    @JoinColumn(name = "id_medic", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_MEDIC")) // en esta tabla se crea la columna id_patient
    private Medic medic;

    @ManyToOne //Referencia a llave foranea
    //Creación de la columna de enlace
    @JoinColumn(name = "id_speciality", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_SPECIALITY")) // en esta tabla se crea la columna id_patient
    private Specialty specialty;

    @Column(nullable = false, length = 3)
    private String numConsult;

    @Column(nullable = false)
    private LocalDateTime consultDate;

    @OneToMany(mappedBy = "consult", cascade = {CascadeType.ALL}) // fetch = FetchType.LAZY)
    private List<ConsultDetail> details;



}
