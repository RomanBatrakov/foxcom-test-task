package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/generate/{count}")
    public List<ApplicationDto> generateApplications(@PathVariable("count") Long count) {
        log.info("POST request for path /applications/generate/{count} with count={}", count);
        return applicationService.generateApplications(count);
    }
}
