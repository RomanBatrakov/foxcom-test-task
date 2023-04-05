package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
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
@RequestMapping(path = "/types")
@RequiredArgsConstructor
public class ResourceTypeController {
    private final ResourceTypeService resourceTypeService;

    @PostMapping
    public ResourceTypeDto createResourceType(@Valid @RequestBody ResourceTypeDto resourceTypeDto) {
        log.info("POST request for path /types with resourceType: {}", resourceTypeDto);
        return resourceTypeService.createResourceType(resourceTypeDto);
    }
}
