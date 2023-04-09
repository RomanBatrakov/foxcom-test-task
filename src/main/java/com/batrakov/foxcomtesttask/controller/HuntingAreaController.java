package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.dto.HuntingAreaDto;
import com.batrakov.foxcomtesttask.service.HuntingAreaService;
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
@RequestMapping(path = "/areas")
@RequiredArgsConstructor
public class HuntingAreaController {
    private final HuntingAreaService huntingAreaService;

    @PostMapping
    public HuntingAreaDto createArea(@Valid @RequestBody HuntingAreaDto huntingAreaDto) {
        log.info("POST request for path /areas with huntingArea: {}", huntingAreaDto);
        return huntingAreaService.createArea(huntingAreaDto);
    }
}
