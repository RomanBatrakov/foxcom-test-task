package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.HuntingAreaRepository;
import com.batrakov.foxcomtesttask.exeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.HuntingAreaMapper;
import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.dto.HuntingAreaDto;
import com.batrakov.foxcomtesttask.service.HuntingAreaService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HuntingAreaServiceImpl implements HuntingAreaService {
    private final HuntingAreaMapper huntingAreaMapper;
    private final HuntingAreaRepository huntingAreaRepository;

    @Override
    public HuntingAreaDto createArea(HuntingAreaDto huntingAreaDto) {
        log.info("Creating area={}", huntingAreaDto);
        HuntingArea newArea = huntingAreaMapper.toHuntingArea(huntingAreaDto);
        try {
            HuntingArea area = huntingAreaRepository.save(newArea);
            return huntingAreaMapper.toHuntingAreaDto(area);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(String.format("Area %s is already exist", huntingAreaDto.getName()));
        }
    }

    @Override
    public HuntingArea findAreaById(Long areaId) {
        try {
            log.info("Finding area by Id={}", areaId);
            return huntingAreaRepository.findById(areaId).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(String.format("Area with id %s is not found", areaId));
        }
    }

    @Override
    public List<HuntingArea> getHuntingAreasByIds(List<Long> huntingAreaIdList) {
        try {
            log.info("Converting HuntingAreas ids: {} to HuntingAreas", huntingAreaIdList);
            return huntingAreaRepository.findHuntingAreaByIdIn(huntingAreaIdList);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("HuntingArea is not found");
        }
    }

    @Override
    public List<HuntingArea> generateHuntingAreas(int count) {
        List<HuntingArea> huntingAreas = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < count; i++) {
            HuntingArea huntingArea = new HuntingArea();
            huntingArea.setName("Russia, " + faker.address().state() + " Hunting Area");
            huntingAreas.add(huntingArea);
        }
        return huntingAreaRepository.saveAll(huntingAreas);
    }
}
