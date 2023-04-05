package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ResourceRepository;
import com.batrakov.foxcomtesttask.dao.ResourceTypeRepository;
import com.batrakov.foxcomtesttask.exeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.ResourceMapper;
import com.batrakov.foxcomtesttask.mapper.ResourceTypeMapper;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
import com.batrakov.foxcomtesttask.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceMapper resourceMapper;
    private final ResourceRepository resourceRepository;

    @Override
    public ResourceDto createResource(ResourceDto resourceDto) {
        log.info("Creating resource={}", resourceDto);
        Resource newResource = resourceMapper.toResource(resourceDto);
        try {
            Resource resource = resourceRepository.save(newResource);
            return resourceMapper.toResourceDto(resource);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(String.format("Resource %s is already exist", resourceDto.toString()));
        }
    }
}
