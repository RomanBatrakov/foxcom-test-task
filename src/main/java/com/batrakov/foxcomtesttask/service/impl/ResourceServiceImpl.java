package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ResourceRepository;
import com.batrakov.foxcomtesttask.exeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.ResourceMapper;
import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.Status;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import com.batrakov.foxcomtesttask.service.HuntingAreaService;
import com.batrakov.foxcomtesttask.service.ResourceService;
import com.batrakov.foxcomtesttask.service.ResourceTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceMapper resourceMapper;
    private final ResourceRepository resourceRepository;
    private final ResourceTypeService resourceTypeService;
    private final HuntingAreaService huntingAreaService;

    @Override
    public ResourceDto createResource(ResourceDto resourceDto) {
        log.info("Creating resource={}", resourceDto);
        ResourceType resourceType = resourceTypeService.findResourceTypeById(resourceDto.getResourceTypeId());
        HuntingArea huntingArea = huntingAreaService.findAreaById(resourceDto.getAreaId());

        Resource newResource = resourceMapper.toResource(resourceDto);
        newResource.setResourceType(resourceType);
        newResource.setArea(huntingArea);
        newResource.setStatus(Status.IN_PROGRESS);

        try {
            Resource resource = resourceRepository.save(newResource);
            return resourceMapper.toResourceDto(resource);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public List<Resource> createResources(List<ResourceDto> resourcesDto) {
        log.info("Creating resources={}", resourcesDto);
        List<Long> resourceTypeIdList = resourcesDto.stream().map(ResourceDto::getResourceTypeId).toList();
        List<Long> huntingAreaIdList = resourcesDto.stream().map(ResourceDto::getAreaId).toList();

        List<ResourceType> resourceTypeList = resourceTypeService.getResourceTypesByIds(resourceTypeIdList);
        List<HuntingArea> huntingAreaList = huntingAreaService.getHuntingAreasByIds(huntingAreaIdList);

        List<Resource> resources = resourceMapper.toResourceList(resourcesDto);

        for (int i = 0; i < resources.size(); i++) {
            resources.get(i).setResourceType(resourceTypeList.get(i));
            resources.get(i).setArea(huntingAreaList.get(i));
        }
        return resourceRepository.saveAll(resources);
    }

    @Override
    public List<Resource> generateResources(int count, List<HuntingArea> huntingAreaList,
                                            List<ResourceType> resourceTypeList) {
        List<Resource> resources = new ArrayList<>();
        Random random = new Random();
        int huntingAreaListSize = huntingAreaList.size();
        int resourceTypeListSize = resourceTypeList.size();
        for (int i = 0; i < count; i++) {
            HuntingArea randomHuntingArea = huntingAreaList.get(random.nextInt(huntingAreaListSize));
            ResourceType randomResourceType = resourceTypeList.get(random.nextInt(resourceTypeListSize));
            Resource resource = new Resource();
            resource.setArea(randomHuntingArea);
            resource.setResourceType(randomResourceType);
            resource.setAmount((long) (random.nextInt(20) + 1));
            resources.add(resource);
        }
        return resourceRepository.saveAll(resources);
    }

    @Override
    public void updateResourcesWithApplications(List<Application> applications) {
        for (Application application : applications) {
            List<Resource> resources = application.getResources();
            for (Resource resource : resources) {
                resource.setApplication(application);
            }
            resourceRepository.saveAll(resources);
        }
    }
}
