package com.heuron.backend.patient.service;

import com.heuron.backend.exception.CustomException;
import com.heuron.backend.patient.domain.Patients;
import com.heuron.backend.patient.domain.PatientsRepository;
import com.heuron.backend.patient.dto.PatientsCreateDto;
import com.heuron.backend.patient.dto.PatientsResponseDto;
import com.heuron.backend.patient.dto.PatientsUpdateDto;
import com.heuron.backend.patient.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PatientsService {

    public static final Logger log = LoggerFactory.getLogger(PatientsService.class);

    @Value("${image.upload.directory}")
    private String uploadDir;

    private final PatientsRepository patientsRepository;


    public Patients findById(Long id) {
        return patientsRepository.findById(id).orElseThrow(() -> new CustomException("Not exist data"));
    }

    @Transactional
    public ResponseEntity createPatient(PatientsCreateDto patientsCreateDto, MultipartFile imgFile) {
        log.info("PatientsRequestDto:{} ,imageFile:{} ", patientsCreateDto,imgFile);

        // entity 생성
        Long id = savePatient(patientsCreateDto);
        String imgPath = "";
        // img 업로드
        try {
            if(null != id) imgPath = uploadImage(id, imgFile);
        } catch (Exception e) {
            return responseFailMsg("fail, image upload", e);
        }

        // entity 변경
        PatientsUpdateDto patientsUpdateDto = PatientsUpdateDto.builder()
                .id(id).imgPath(imgPath).build();
        try {
            if(!imgPath.isEmpty()) updatePatientImageUrl(patientsUpdateDto);
        } catch (Exception e) {
            return responseFailMsg("fail, entity update", e);
        }

        return responseSuccessMsg("sucess, data save");
    }

    private Long savePatient(PatientsCreateDto patientsCreateDto){
        return patientsRepository.save(patientsCreateDto.toEntity()).getId();
    }

    private String uploadImage(Long id, MultipartFile imgFile) {

        if (imgFile.isEmpty()) {
           throw new CustomException("Not exist image file");
        }

        String projectPath = System.getProperty("user.dir").replace("\\", "/");
        String extension = "." + FilenameUtils.getExtension(imgFile.getOriginalFilename()); // 확장자 추출

        if (!extension.matches("\\.(png|jpeg|jpg)$")) {
            throw new CustomException("Invalid file format");
        }

        String imgPath = uploadDir + "/" + id + extension;  // DB에 저장할 이미지 경로
        String filePath = projectPath + "/src/main/resources" + imgPath;  // 실제 파일 저장 경로

        try {

            File dest = new File(filePath);

            // 디렉토리가 없으면 생성
            if (!dest.exists()) {
                dest.mkdirs();
            }

            imgFile.transferTo(dest);

        } catch (IOException e) {
            throw new CustomException("fail upload image");
        }

        return imgPath;

    }
    private void updatePatientImageUrl(PatientsUpdateDto patientsUpdateDto) {
        Patients patients = findById(patientsUpdateDto.getId());
        patients.updateImagePath(patientsUpdateDto.getImgPath());
        // patientsRepository.save(patients); // Transactional 어노테이션을 붙여주면 Dirty Checking을 하게 되고, 데이터베이스에 commit을 해서 수정된 사항을 save 없이도 반영할 수 있도록 한다.
    }

    public ResponseEntity getPatient(Long id){  // transaction readonly true 넣을 지 고민
        Patients patients = findById(id);

        if (patients.isEmptyImgPath()) {
            return responseFailMsg("fail, select data");
        }

        return responseSuccessMsg("sucess, select save", patients.toDto());
    }

    @Transactional
    public ResponseEntity deletePatient(Long id) {
        Patients patients = findById(id);
        try {
            patientsRepository.delete(patients);
            return responseSuccessMsg("sucess, delete save");
        } catch (Exception e) {
            return responseFailMsg("fail, delete data",e);
        }
    }

    public ResponseEntity getPatientImg(Long id) {
        // domain 조회 한 값을 기준으로 서버 내부에 저장되어있는 파일 탐색, return img

        
        String projectPath = System.getProperty("user.dir").replace("\\", "/");
        String basePath = projectPath + "/src/main/resources/";
        String imagePath =  findById(id).getImgPath();
        Path imageFilePath = Paths.get(basePath+imagePath);

        String[] parts = imagePath.split("/");
        String imageName = parts[parts.length - 1];

        try {

            // 이미지 파일을 읽어와서 Resource로 변환
            Resource resource = new UrlResource(imageFilePath.toUri());

            // 이미지 파일이 존재하면 응답으로 보냄
            if (resource.exists() && resource.isReadable()) {
                // 이미지의 확장자에 따라 Content-Type 설정
                String contentType = "";
                if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
                    contentType = MediaType.IMAGE_JPEG_VALUE;
                } else if (imageName.endsWith(".png")) {
                    contentType = MediaType.IMAGE_PNG_VALUE;
                } else {
                    return  responseFailMsg("fail, Invalid file format");
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return  responseFailMsg("fail, Not exist image data");
            }
        } catch (MalformedURLException e) {

            return responseFailMsg("fail, view image" ,e);
        }

    }

    private ResponseEntity responseFailMsg(String msg, Exception e) {
        log.error(msg, e);
        return ResponseEntity.internalServerError()
                .body(ResponseDto.builder().result(false).msg(msg).build());
    }

    private ResponseEntity responseFailMsg(String msg) {
        return ResponseEntity.internalServerError()
                .body(ResponseDto.builder().result(false).msg(msg).build());
    }


    private ResponseEntity responseSuccessMsg(String msg) {
        return ResponseEntity.ok()
                .body(ResponseDto.builder().result(true).msg(msg).build());
    }

    private ResponseEntity responseSuccessMsg(String msg, PatientsResponseDto dto) {
        return ResponseEntity.ok()
                .body(ResponseDto.builder().result(true).data(dto).msg(msg).build());
    }

}
