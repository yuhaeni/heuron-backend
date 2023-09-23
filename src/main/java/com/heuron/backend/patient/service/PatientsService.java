package com.heuron.backend.patient.service;

import com.heuron.backend.exception.ExceptionSupplier;
import com.heuron.backend.patient.dto.PatientsCreateDto;
import com.heuron.backend.patient.domain.Patients;
import com.heuron.backend.patient.domain.PatientsRepository;
import com.heuron.backend.patient.dto.PatientsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PatientsService {

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    private final PatientsRepository patientsRepository;

    public Patients findById(Long id) {
        return patientsRepository.findById(id).orElseThrow(() -> new RuntimeException("Not exist data"));
    }

    @Transactional
    public Long savePatients(PatientsCreateDto patientsCreateDto){
        return patientsRepository.save(patientsCreateDto.toEntity()).getId();
    }

    @Transactional
    public String uploadImage(Long id, MultipartFile imgFile) {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = currentDate.format(formatter);

        String saveFileName = id + "-patient-image" + imgFile.getOriginalFilename();
        String filePath = uploadDirectory + formattedDate + File.separator + saveFileName;

        try {

            boolean result = false;
            File dest = new File(filePath);

            //디렉토리가 없으면 생성
            if(!dest.exists()) {
                result = dest.mkdirs();
            }

            // 파일을 지정된 경로로 복사
            if(result) {
                imgFile.transferTo(dest);
            }

        } catch (IOException e) {
            //TODO error 핸들링 필요
            throw new RuntimeException(e);
        }

        return filePath;

    }

    @Transactional
    public void updatePatientImageUrl(PatientsUpdateDto patientsUpdateDto) {
        Patients patients = findById(patientsUpdateDto.getId());
        patients.updateImagePath(patientsUpdateDto.getImgPath());
        // patientsRepository.save(patients); // Transactional 어노테이션을 붙여주면 Dirty Checking을 하게 되고, 데이터베이스에 commit을 해서 수정된 사항을 save 없이도 반영할 수 있도록 한다.
    }

}
