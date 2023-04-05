package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.HuntingAreaRepository;
import com.batrakov.foxcomtesttask.exeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.HuntingAreaMapper;
import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.dto.HuntingAreaDto;
import com.batrakov.foxcomtesttask.service.HuntingAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
}
