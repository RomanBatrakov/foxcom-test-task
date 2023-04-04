package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.dto.NewApplicationDto;
import com.batrakov.foxcomtesttask.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public Application createApplication(@Valid @RequestBody NewApplicationDto newApplicationDto) {
        return applicationService.createApplication(newApplicationDto);
    }
}
