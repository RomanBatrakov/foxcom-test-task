package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.Hunter;
import com.batrakov.foxcomtesttask.model.dto.NewHunterDto;

public interface HunterService {
    Hunter findHunterById(Long hunterId);

    Hunter createHunter(NewHunterDto newHunterDto);
}
