package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.HuntingAreaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class HuntingAreaControllerTest {
    @Autowired
    ObjectMapper mapper;
    private MockMvc mvc;
    @Autowired
    private HuntingAreaController huntingAreaController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(huntingAreaController).build();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createArea_shouldOk() throws Exception {
        HuntingAreaDto dto = HuntingAreaDto.builder().name("Moscow").build();
        var result = mvc.perform(post("/areas").content(mapper.writeValueAsString(dto))
                                               .characterEncoding(StandardCharsets.UTF_8)
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var huntingAreaDto = mapper.readValue(result.getResponse().getContentAsString(), HuntingAreaDto.class);
        log.debug("Created area: {}", huntingAreaDto);

        assertThat(huntingAreaDto.getId(), is(1L));
    }

    @Sql("classpath:HuntingAreaControllerTest.createAreaWithSameName_should409.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createAreaWithSameName_should409() {
        HuntingAreaDto dto = HuntingAreaDto.builder().name("Moscow").build();
        assertThrows(jakarta.servlet.ServletException.class, () -> {
            mvc.perform(post("/areas").content(mapper.writeValueAsString(dto))
                                      .characterEncoding(StandardCharsets.UTF_8)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
        });
    }
}
