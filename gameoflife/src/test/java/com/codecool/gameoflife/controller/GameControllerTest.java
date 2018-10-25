package com.codecool.gameoflife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void controllerExists() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void initPostExists() throws Exception {

        String content = mapper.writeValueAsString(new GameController.DTO(""));

        mockMvc.perform(
                    post("/init")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Disabled
    public void nextGetExists() throws Exception {
        mockMvc.perform(get("/next"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void initAndNextStateIsValid() throws Exception {

        String content = mapper.writeValueAsString(
                new GameController.DTO("_*_\n_*_\n_*_"));

        mockMvc.perform(
                    post("/init")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content)
        ).andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/next"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data").value("___\n***\n___"));
    }

}
