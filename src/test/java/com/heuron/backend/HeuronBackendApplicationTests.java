package com.heuron.backend;

import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@SpringBootTest
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc
class HeuronBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void savePatients() throws Exception {


        String jsonStr = "";

        ResultActions result = mockMvc.perform(post("/api/test/v1/xss-test/dto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStr))
                .andDo(print());

    }

}
