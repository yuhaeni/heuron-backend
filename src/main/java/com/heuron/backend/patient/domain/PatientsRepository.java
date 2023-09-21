package com.heuron.backend.patient.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface PatientsRepository extends JpaRepository<Patients ,Long> {

    @Modifying
    @Query("UPDATE Patients p SET p.imgPath = :imgPath WHERE p.id = :id")
    void updatePatientImgUrl(@Param("id") Long patientId, @Param("imgPath") String imgPath);

}
