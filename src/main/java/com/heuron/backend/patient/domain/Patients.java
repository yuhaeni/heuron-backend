package com.heuron.backend.patient.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
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

    @CreatedDate
    private Timestamp createDate;

    @Builder
    public Patients(String name, int age, String gender, String diseaseFlag, String imgPath) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diseaseFlag = diseaseFlag;
        this.imgPath = imgPath;
    }
}
