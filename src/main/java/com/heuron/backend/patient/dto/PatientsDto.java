package com.heuron.backend.patient.dto;

import com.heuron.backend.patient.domain.Patients;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class PatientsDto {
    private String name;
    private int age;
    private String gender;
    private String diseaseFlag;
    private String imgPath;

    public Patients toEntity() {
        return Patients.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .diseaseFlag(diseaseFlag)
                .imgPath(imgPath)
                .build();
    }
}
