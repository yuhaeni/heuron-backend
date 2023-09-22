package com.heuron.backend.controller;

import com.heuron.backend.dto.PatientsRequestDto;
import com.heuron.backend.service.PatientsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/heuron/v1")
public class PatientsController {

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;

    private PatientsService patientsService;
    private final static String DEFAULT_ERR_MSG = "no errors";

    public PatientsController() {
    }

    @PostMapping(value = "/patients" ,consumes = {"multipart/form-data"})
    public ResponseEntity<?> savePatient(@RequestPart("json") PatientsRequestDto patientsRequestDto, @RequestPart("imageFile") MultipartFile imgFile, HttpServletResponse resp, HttpServletRequest req){

        Map<String, Object> result = new HashMap<>();
        Long id = patientsService.savePatients(patientsRequestDto);
        String imgPath = "";

        try {
            if(null != id) imgPath=patientsService.uploadImage(patientsRequestDto,imgFile);
        } catch (Exception e) {
            result.put("result", false);
            result.put("msg", "이미지 업로드 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        try {
            if(!imgPath.isEmpty()) patientsService.updatePatientImageUrl(id ,imgPath);
        } catch (Exception e) {
            result.put("result", false);
            result.put("msg", "환자 이미지 정보 업데이트 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        result.put("result", true);
        result.put("msg", "");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}
