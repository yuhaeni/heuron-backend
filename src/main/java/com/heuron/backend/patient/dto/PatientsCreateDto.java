package com.heuron.backend.patient.dto;

import com.heuron.backend.patient.domain.Patients;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class PatientsCreateDto {
    private String name;
    private int age;
    private String gender;
    private String diseaseFlag;

    public PatientsCreateDto(String name, int age, String gender, String diseaseFlag) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diseaseFlag = diseaseFlag;
    }

    public Patients toEntity() {
        return Patients.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .diseaseFlag(diseaseFlag)
                .build();
    }
}
