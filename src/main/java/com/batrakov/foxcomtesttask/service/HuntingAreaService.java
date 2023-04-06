package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.dto.HuntingAreaDto;

import java.util.List;

public interface HuntingAreaService {
    HuntingAreaDto createArea(HuntingAreaDto huntingAreaDto);

    HuntingArea findAreaById(Long areaId);

    List<HuntingArea> getHuntingAreasByIds(List<Long> huntingAreaIdList);

    List<HuntingArea> generateHuntingAreas(int count);
}
