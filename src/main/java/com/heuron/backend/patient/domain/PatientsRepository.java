package com.heuron.backend.patient.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patients ,Long> {
}
