package com.heuron.backend.patient.dto;

import com.heuron.backend.patient.domain.Patients;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@AllArgsConstructor
@Builder
public class PatientsCreateDto {


    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Age is required.")
    private int age;

    @Pattern(regexp = "^[MW]$", message = "Invalid gender. Should be 'M' or 'W'.")
    @NotBlank(message = "Gender is required.")
    private String gender;

    @Pattern(regexp = "^[YN]$", message = "Invalid disease flag. Should be 'Y' or 'N'.")
    @NotBlank(message = "Disease flag is required.")
    private String diseaseFlag;


    public Patients toEntity() {
        return Patients.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .diseaseFlag(diseaseFlag)
                .build();
    }
}
