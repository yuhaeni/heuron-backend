package com.heuron.backend;

import com.heuron.backend.patient.dto.PatientsCreateDto;
import com.heuron.backend.patient.dto.PatientsResponseDto;
import com.heuron.backend.patient.dto.PatientsGetRequestDto;
import com.heuron.backend.patient.service.PatientsService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ContextConfiguration(classes = HeuronBackendApplication.class)
@AutoConfigureMockMvc
class HeuronBackendApplicationTests {

    public static final Logger log = LoggerFactory.getLogger(HeuronBackendApplicationTests.class);

    @Autowired
    PatientsService patientsService;

    @Test
    void savePatient() throws Exception {
        PatientsCreateDto dto = PatientsCreateDto.builder().name("유해니").age(26).diseaseFlag("N").gender("W").build();
        Long id = patientsService.savePatient(dto);
        // 결과 검증
        assertNotNull(id); // id 값이 null이 아닌지 확인
    }

    @Test
    void getPatientDetail() throws Exception {
        ResponseEntity<?> responseEntity = patientsService.getPatient(70L);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful()); // HTTP 상태 코드가 2xx(성공)인지 확인
    }

    @Test
    void deletePatient() throws Exception {
        ResponseEntity<?> responseEntity = patientsService.deletePatient(61L);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

}
