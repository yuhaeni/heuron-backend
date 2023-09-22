package com.heuron.backend;

import com.heuron.backend.dto.PatientsRequestDto;
import com.heuron.backend.service.PatientsService;
import javafx.application.Application;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ContextConfiguration(classes = HeuronBackendApplication.class)
@AutoConfigureMockMvc
class HeuronBackendApplicationTests {

    public static final Logger log = LoggerFactory.getLogger(HeuronBackendApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PatientsService patientsService;

    @Test
    void savePatients() throws Exception {

        PatientsRequestDto dto = new PatientsRequestDto("김옥순", 38, "W", "Y" ,"");
        Long id = patientsService.savePatients(dto);
        log.info("id:{}",id);

    }

    @Test
    void savePatientsAll() throws Exception {

        String jsonStr = "{\"name\": \"김옥순\", \"age\": \"38\", \"gender\": \"W\", \"diseaseFlag\": \"Y\"}";
        MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image-content".getBytes());

        try {
            ResultActions result = mockMvc.perform(
                            MockMvcRequestBuilders.multipart("/heuron/v1/patients")
                                    .file(imageFile) // 이미지 파일 포함
                                    .param("patientInfo", jsonStr)  // JSON 문자열 포함
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
