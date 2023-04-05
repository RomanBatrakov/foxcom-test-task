package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ResourceTypeRepository;
import com.batrakov.foxcomtesttask.exeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.ResourceTypeMapper;
import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
import com.batrakov.foxcomtesttask.service.ResourceTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceTypeServiceImpl implements ResourceTypeService {
    private final ResourceTypeMapper resourceTypeMapper;
    private final ResourceTypeRepository resourceTypeRepository;

    @Override
    public ResourceTypeDto createResourceType(ResourceTypeDto resourceTypeDto) {
        log.info("Creating resourceType={}", resourceTypeDto);
        ResourceType newResourceType = resourceTypeMapper.toResourceType(resourceTypeDto);
        try {
            ResourceType resourceType = resourceTypeRepository.save(newResourceType);
            return resourceTypeMapper.toResourceTypeDto(resourceType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(String.format("Type %s is already exist", resourceTypeDto.getName()));
        }
    }
}
