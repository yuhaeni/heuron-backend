package com.heuron.backend.dto;

import com.heuron.backend.patient.domain.Patients;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PatientsRequestDto {
    private String name;
    private int age;
    private String gender;
    private String diseaseFlag;
    //private String imgPath;

    @Builder
    public PatientsRequestDto(String name, int age, String gender, String diseaseFlag) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diseaseFlag = diseaseFlag;
        //this.imgPath = imgPath;
    }

    public Patients toEntity() {
        return Patients.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .diseaseFlag(diseaseFlag)
                //.imgPath(imgPath)
                .build();
    }
}
