package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ApplicationDto createApplication(@Valid @RequestBody ApplicationDto applicationDto) {
        log.info("POST request for path /applications with application: {}", applicationDto);
        return applicationService.createApplication(applicationDto);
    }

    @GetMapping("/{appId}")
    public ApplicationDto getApplicationById(@PathVariable("appId") Long id) {
        log.info("GET application for path /applications/{appId} with id={}", id);
        return applicationService.getApplicationById(id);
    }

    @GetMapping()
    public List<ApplicationDto> getAllApplications() {
        log.info("GET applications for path /applications");
        return applicationService.getAllApplications();
    }

    @DeleteMapping("/{appId}")
    public void deleteApplicationById(@PathVariable("appId") Long id) {
        log.info("DELETE application for path /applications/{appId} with id={}", id);
        applicationService.deleteApplication(id);
    }

    @PostMapping("/generate/{count}")
    public List<ApplicationDto> generateApplications(@PathVariable("count") Long count) {
        log.info("POST request for path /applications/generate/{count} with count={}", count);
        return applicationService.generateApplications(count);
    }

    @PostMapping("/checking/{flag}")
    public void checkingApplications(@PathVariable("flag") boolean flag) {
        log.info("POST request for path /applications/checking/{flag} with flag={}", flag);
        return applicationService.checkingApplications(flag);
    }
}
