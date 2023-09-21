package com.heuron.backend.service;

import com.heuron.backend.dto.PatientsRequestDto;
import com.heuron.backend.patient.domain.Patients;
import com.heuron.backend.patient.domain.PatientsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Service
public class PatientsService {

    @Value("${upload.directory}")
    private String uploadDirectory;
    private PatientsRepository patientsRepository;

    public Long savePatients(PatientsRequestDto patientsRequestDto){

        Patients patients = patientsRequestDto.toEntity();
        Patients savedPatients = patientsRepository.save(patients);

        return savedPatients.getId();
    }

    public String uploadImage(PatientsRequestDto patientsRequestDto ,MultipartFile imgFile) {


        Long patientId = patientsRequestDto.toEntity().getId();

        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
        String formattedDate = dateFormat.format(now);

        String saveFileName = patientId+"-patient-image"+imgFile.getOriginalFilename();
        String filePath = uploadDirectory + formattedDate + File.separator + saveFileName;

        try {

            boolean result = false;
            File dest = new File(filePath);

            //디렉토리가 없으면 생성
            if(!dest.exists()) result = dest.mkdirs();

            // 파일을 지정된 경로로 복사
            if(result)  imgFile.transferTo(dest);

        } catch (IOException e) {
            //TODO error 핸들링 필요
            throw new RuntimeException(e);
        }

        return filePath;

    }

    @Transactional
    public void updatePatientImageUrl(Long patientId, String imgPath) {
        patientsRepository.updatePatientImgUrl(patientId, imgPath);
    }

}
