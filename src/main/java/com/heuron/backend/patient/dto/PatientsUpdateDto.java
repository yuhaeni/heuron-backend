package com.heuron.backend.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PatientsUpdateDto {    // builder vs superbuilder 공부
    private Long id;
    private String imgPath;

}
