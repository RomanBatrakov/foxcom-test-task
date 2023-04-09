package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ResourceTypeRepository;
import com.batrakov.foxcomtesttask.exсeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.ResourceTypeMapper;
import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
import com.batrakov.foxcomtesttask.service.ResourceTypeService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Transactional
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

    @Override
    public ResourceType findResourceTypeById(Long resourceTypeId) {
        try {
            log.info("Finding resourceType by Id={}", resourceTypeId);
            return resourceTypeRepository.findById(resourceTypeId).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(String.format("ResourceType with id %s is not found", resourceTypeId));
        }
    }

    @Override
    public List<ResourceType> getResourceTypesByIds(List<Long> resourceTypeIdList) {
        try {
            log.info("Converting resourceTypes ids: {} to resourceTypes", resourceTypeIdList);
            return resourceTypeRepository.findResourceTypeByIdIn(resourceTypeIdList);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("ResourceType is not found");
        }
    }

    @Override
    public List<ResourceType> getAllResourceType() {
        log.info("Finding all resourceTypes");
        return resourceTypeRepository.findAll();
    }

    @Override
    public List<ResourceType> generateResourceTypes(int count) {
        List<ResourceType> resourceTypes = resourceTypeRepository.findAll();
        if (resourceTypes.isEmpty()) {
            Faker faker = new Faker();
            Set<String> usedNames = new HashSet<>();
            for (int i = 0; i < count; i++) {
                String name;
                do {
                    name = faker.animal().name();
                } while (usedNames.contains(name));
                usedNames.add(name);
                int daysToAdd = faker.number().numberBetween(1, 364);

                ResourceType resourceType = new ResourceType();
                resourceType.setName(name);
                resourceType.setQuota((long) faker.number().numberBetween(50, 300));
                resourceType.setStartDate(LocalDate.now().plusDays(daysToAdd));
                resourceType.setEndDate(LocalDate.now().plusDays(daysToAdd + 90));
                resourceTypes.add(resourceType);
            }
            return resourceTypeRepository.saveAll(resourceTypes);
        } else {
            return resourceTypes;
        }
    }
}
