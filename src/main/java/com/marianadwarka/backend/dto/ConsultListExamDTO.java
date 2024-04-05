package com.marianadwarka.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultListExamDTO {

    //Al enlazar la consulta se enlaza el detalle por dentro
    @NotNull
    private ConsultDTO consult;

    //Listado de exámenes que indica el m{edico
    @NotNull
    private List<ExamDTO> lstExam;
}
