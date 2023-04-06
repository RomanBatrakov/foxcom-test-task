package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;

import java.util.List;

public interface ResourceTypeService {
    ResourceTypeDto createResourceType(ResourceTypeDto resourceTypeDto);

    ResourceType findResourceTypeById(Long resourceTypeId);

    List<ResourceType> getResourceTypesByIds(List<Long> resourceTypeIdList);

    List<ResourceType> generateResourceTypes(int count);
}
