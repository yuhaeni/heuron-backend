package com.heuron.backend.patient.service;

import com.heuron.backend.patient.dto.PatientsCreateDto;
import com.heuron.backend.patient.domain.Patients;
import com.heuron.backend.patient.domain.PatientsRepository;
import com.heuron.backend.patient.dto.PatientsDto;
import com.heuron.backend.patient.dto.PatientsGetRequestDto;
import com.heuron.backend.patient.dto.PatientsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PatientsService {

    @Value("${image.upload.directory}")
    private String uploadDir;

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

        if (imgFile.isEmpty()) {
            throw new RuntimeException("파일을 선택하세요.");
        }

        String projectPath = System.getProperty("user.dir").replace("\\", "/");

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = currentDate.format(formatter);

        String saveFileName = id + "-patient-image" + imgFile.getOriginalFilename();
        String filePath = projectPath + "/" + uploadDir +"/"+ formattedDate + "/" + saveFileName;


        try {

            File dest = new File(filePath);

            // 디렉토리가 없으면 생성
            if (!dest.exists()) {
                dest.mkdirs();
            }

            imgFile.transferTo(dest);

        } catch (IOException e) {
            throw  new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
        }

        return filePath;

    }

    @Transactional
    public void updatePatientImageUrl(PatientsUpdateDto patientsUpdateDto) {
        Patients patients = findById(patientsUpdateDto.getId());
        patients.updateImagePath(patientsUpdateDto.getImgPath());
        // patientsRepository.save(patients); // Transactional 어노테이션을 붙여주면 Dirty Checking을 하게 되고, 데이터베이스에 commit을 해서 수정된 사항을 save 없이도 반영할 수 있도록 한다.
    }

    public PatientsDto getPatientsDetail(PatientsGetRequestDto patientsGetRequestDto){
        Patients patients = findById(patientsGetRequestDto.getId());

        if (patients.getImgPath().isEmpty()) {
            throw new RuntimeException("조회 가능한 데이터가 없습니다.");
        }

        return patients.toDto();
    }

}
