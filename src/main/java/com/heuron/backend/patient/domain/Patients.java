package com.heuron.backend.patient.domain;

import com.heuron.backend.patient.dto.PatientsResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Size(max = 50)
    private String name;

    @Column
    @Digits(integer = 3, fraction = 0)
    private int age;

    @Column(columnDefinition = "char(1) default('M')")
    private String gender;

    @Column(name = "disease_flag" ,columnDefinition = "char(1) default('N')")
    private String diseaseFlag;

    @Column(name = "img_path")
    @Size(max = 300)
    private String imgPath;

    @CreationTimestamp
    @Column(name = "create_date" , updatable = false , nullable = false)
    private Timestamp createDate;

    public void updateImagePath(String imgPath) {
        this.imgPath = imgPath;
    }

    public PatientsResponseDto toDto() {
        return PatientsResponseDto.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .diseaseFlag(diseaseFlag)
                .imgPath(imgPath)
                .build();
    }

    public boolean isEmptyImgPath() {
        return this.imgPath.isEmpty();
    }

}
