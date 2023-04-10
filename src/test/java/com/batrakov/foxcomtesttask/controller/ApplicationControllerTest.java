package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.Status;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Collections;
import java.util.List;

import static com.batrakov.foxcomtesttask.model.ApplicationCategory.LOTTERY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class ApplicationControllerTest {
    @Autowired
    ObjectMapper mapper;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Sql("classpath:ApplicationControllerTest.createApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createApplication_shouldOk() throws Exception {
        var dto = buildApplicationDto();
        var result = mvc.perform(post("/applications").content(mapper.writeValueAsString(dto))
                                                      .characterEncoding(StandardCharsets.UTF_8)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var applicationDto = mapper.readValue(result.getResponse().getContentAsString(), ApplicationDto.class);
        log.debug("Created application: {}", applicationDto);

        assertThat(applicationDto.getId(), is(1L));
        assertThat(applicationDto.getStatus(), is(Status.IN_PROGRESS));
    }

    @Sql("classpath:ApplicationControllerTest.createApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createApplication_shouldClientError() throws Exception {
        var dto = buildApplicationDto();
        dto.setFullName("");
        mvc.perform(post("/applications").content(mapper.writeValueAsString(dto))
                                         .characterEncoding(StandardCharsets.UTF_8)
                                         .contentType(MediaType.APPLICATION_JSON)
                                         .accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Sql("classpath:ApplicationControllerTest.updateApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateApplication_shouldOk() throws Exception {
        var dto = buildUpdateApplicationDto();
        var result = mvc.perform(patch("/applications").content(mapper.writeValueAsString(dto))
                                                       .characterEncoding(StandardCharsets.UTF_8)
                                                       .contentType(MediaType.APPLICATION_JSON)
                                                       .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var applicationDto = mapper.readValue(result.getResponse().getContentAsString(), ApplicationDto.class);
        log.debug("Update application: {}", applicationDto);

        assertThat(applicationDto.getId(), is(1L));
        assertThat(applicationDto.getStatus(), is(Status.IN_PROGRESS));
        assertThat(applicationDto.getFullName(), is("Update"));
    }

    @Sql("classpath:ApplicationControllerTest.updateApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateApplication_should404() throws Exception {
        var dto = buildUpdateApplicationDto();
        dto.setId(2L);
        mvc.perform(patch("/applications").content(mapper.writeValueAsString(dto))
                                          .characterEncoding(StandardCharsets.UTF_8)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Sql("classpath:ApplicationControllerTest.updateApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getApplicationById_shouldOk() throws Exception {
        var result = mvc.perform(get("/applications/1").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var applicationDto = mapper.readValue(result.getResponse().getContentAsString(), ApplicationDto.class);
        log.debug("Get application: {}", applicationDto);

        assertThat(applicationDto.getId(), is(1L));
        assertThat(applicationDto.getStatus(), is(Status.APPROVED));
    }

    @Sql("classpath:ApplicationControllerTest.updateApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllApplications_shouldOk() throws Exception {
        var result = mvc.perform(get("/applications").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var applications =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ApplicationDto>>() {
                });
        log.debug("Get applications: {}", applications);

        assertThat(applications.size(), is(1));
    }

    @Sql("classpath:ApplicationControllerTest.updateApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteApplicationById_shouldOk() throws Exception {
        mvc.perform(delete("/applications/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mvc.perform(get("/applications/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void generateApplications_shouldOk() throws Exception {
        var result = mvc.perform(post("/applications/generate/5").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        var applications =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ApplicationDto>>() {
                });
        log.debug("Generate applications: {}", applications);

        assertThat(applications.size(), is(5));
    }

    @Sql("classpath:ApplicationControllerTest.checkApplication.sql")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void checkingApplications_shouldApproved() throws Exception {
        mvc.perform(post("/applications/checking/true").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andReturn();
        var result = mvc.perform(get("/applications").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        var applications =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ApplicationDto>>() {
                });
        log.debug("Checking applications: {}", applications);

        assertThat(applications.get(0).getStatus(), is(Status.APPROVED));
    }

    private ApplicationDto buildApplicationDto() {
        return ApplicationDto.builder()
                             .fullName("Test test")
                             .category(String.valueOf(LOTTERY))
                             .ticketNumber(10000)
                             .ticketSeries("AAA")
                             .issueDate(LocalDate.now().minusYears(2))
                             .resources(Collections.singletonList(
                                     ResourceDto.builder().resourceTypeId(1L).areaId(1L).amount(1L).build()))
                             .build();
    }

    private ApplicationDto buildUpdateApplicationDto() {
        return ApplicationDto.builder()
                             .id(1L)
                             .fullName("Update")
                             .category(String.valueOf(LOTTERY))
                             .ticketNumber(10000)
                             .ticketSeries("AAA")
                             .issueDate(LocalDate.now().minusYears(2))
                             .resources(Collections.singletonList(
                                     ResourceDto.builder().id(1L).resourceTypeId(1L).areaId(1L).amount(1L).build()))
                             .build();
    }
}
