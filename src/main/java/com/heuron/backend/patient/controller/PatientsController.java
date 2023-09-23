package com.heuron.backend.patient.controller;

import com.heuron.backend.patient.dto.PatientsCreateDto;
import com.heuron.backend.patient.dto.PatientsUpdateDto;
import com.heuron.backend.patient.service.PatientsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/heuron/v1")
public class PatientsController {

    public static final Logger log = LoggerFactory.getLogger(PatientsController.class);

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;

    private final PatientsService patientsService;
    private final static String DEFAULT_ERR_MSG = "no errors";

    @GetMapping(value = "/test")
    public String test(){
      return "test!";
    }

    @PostMapping(value = "/patients" ,consumes = {"multipart/form-data"})
    public ResponseEntity<?> savePatient(@RequestPart("json") PatientsCreateDto patientsCreateDto, @RequestPart("imageFile") MultipartFile imgFile, HttpServletRequest req){

        log.info("PatientsRequestDto:{} ,imageFile:{} ", patientsCreateDto,imgFile);

        Map<String, Object> result = new HashMap<>();

        Long id = patientsService.savePatients(patientsCreateDto);
        String imgPath = "";

        try {
            if(null != id) imgPath = patientsService.uploadImage(id, imgFile);
        } catch (Exception e) {
            result.put("result", false);
            result.put("msg", "이미지 업로드 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        PatientsUpdateDto patientsUpdateDto = PatientsUpdateDto.builder()
                .id(id).imgPath(imgPath).build();

        try {
            if(!imgPath.isEmpty()) patientsService.updatePatientImageUrl(patientsUpdateDto);
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
