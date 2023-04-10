package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
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
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class ResourceTypeControllerTest {
    @Autowired
    ObjectMapper mapper;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createResourceType_shouldOk() throws Exception {
        ResourceTypeDto dto = ResourceTypeDto.builder()
                                             .name("tiger")
                                             .quota(100L)
                                             .startDate(LocalDate.now())
                                             .endDate(LocalDate.now().plusDays(100))
                                             .build();

        var result = mvc.perform(post("/types").content(mapper.writeValueAsString(dto))
                                               .characterEncoding(StandardCharsets.UTF_8)
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var resourceTypeDto = mapper.readValue(result.getResponse().getContentAsString(), ResourceTypeDto.class);
        log.debug("Created area: {}", resourceTypeDto);

        assertThat(resourceTypeDto.getId(), is(1L));
    }

    @Sql("classpath:ResourceTypeControllerTest.createResourceTypeWithSameName_should409.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createResourceTypeWithSameName_should409() throws Exception {
        ResourceTypeDto dto = ResourceTypeDto.builder().name("tiger").quota(100L).build();
        mvc.perform(post("/types").content(mapper.writeValueAsString(dto))
                                  .characterEncoding(StandardCharsets.UTF_8)
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

}