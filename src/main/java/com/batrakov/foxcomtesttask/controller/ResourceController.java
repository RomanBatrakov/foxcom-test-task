package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
import com.batrakov.foxcomtesttask.service.ResourceService;
import com.batrakov.foxcomtesttask.service.ResourceTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @PostMapping
    public ResourceDto createResource(@Valid @RequestBody ResourceDto resourceDto) {
        log.info("POST request for path /resources with resource: {}", resourceDto);
        return resourceService.createResource(resourceDto);
    }
}
