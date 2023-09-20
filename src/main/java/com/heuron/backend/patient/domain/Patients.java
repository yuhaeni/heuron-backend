package com.heuron.backend.patient.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String gender;

    @Column(name = "disease_flag")
    private String diseaseFlag;

    @Column(name = "image_Path")
    private String imagePath;

    @Builder
    public Patients(String name, int age, String gender, String diseaseFlag, String imagePath) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diseaseFlag = diseaseFlag;
        this.imagePath = imagePath;
    }
}
