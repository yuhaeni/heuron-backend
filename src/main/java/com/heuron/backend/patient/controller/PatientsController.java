package com.heuron.backend.patient.controller;

import com.heuron.backend.patient.dto.PatientsCreateDto;
import com.heuron.backend.patient.dto.PatientsResponseDto;
import com.heuron.backend.patient.service.PatientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/heuron/v1/patients")
public class PatientsController {

    private final PatientsService patientsService;
    private final static String DEFAULT_ERR_MSG = "no errors";

    @Value("${image.upload.directory}")
    private String uploadDir;

    /* 저장 API */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPatient(@RequestPart("data") @Valid PatientsCreateDto patientsCreateDto, @RequestPart("img") MultipartFile imgFile) {
        return patientsService.createPatient(patientsCreateDto ,imgFile);
    }

    /* 상세 조회 API */
    @GetMapping("/{id}")
    public ResponseEntity<PatientsResponseDto> getPatient(@PathVariable Long id) {
        return patientsService.getPatient(id);
    }


    /* 이미지 조회 API */
    @GetMapping("/images/{id}")
    public ResponseEntity getPatientImg(@PathVariable Long id)  {
        return patientsService.getPatientImg(id);
    }

    /* 삭제 API */
    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable Long id)  {
        return patientsService.deletePatient(id);
    }

}
