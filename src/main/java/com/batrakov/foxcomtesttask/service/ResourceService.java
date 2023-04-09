package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;

import java.util.List;

public interface ResourceService {
    ResourceDto createResource(ResourceDto resourceDto);

    List<Resource> createResources(List<ResourceDto> resourcesDto);

    List<Resource> generateResources(int count, List<HuntingArea> huntingAreaList, List<ResourceType> resourceTypeList);

    void updateResourcesWithApplications(List<Application> applications);

    List<Resource> updateResources(List<ResourceDto> resourceDtoList, List<Resource> resourceList);
}
