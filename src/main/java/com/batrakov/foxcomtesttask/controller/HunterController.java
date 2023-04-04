package com.batrakov.foxcomtesttask.controller;

import com.batrakov.foxcomtesttask.model.Hunter;
import com.batrakov.foxcomtesttask.model.dto.NewHunterDto;
import com.batrakov.foxcomtesttask.service.HunterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/hunters")
@RequiredArgsConstructor
public class HunterController {
    private final HunterService hunterService;

    @PostMapping
    public Hunter createHunter(@Valid @RequestBody NewHunterDto newHunterDto) {
        return hunterService.createHunter(newHunterDto);
    }
}
