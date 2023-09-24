package com.heuron.backend.patient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientsResponseDto {
    private String name;
    private int age;
    private String gender;
    private String diseaseFlag;
    private String imgPath;

}
