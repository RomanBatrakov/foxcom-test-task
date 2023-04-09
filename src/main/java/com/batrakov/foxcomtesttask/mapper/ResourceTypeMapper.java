package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceTypeMapper {
    ResourceType toResourceType(ResourceTypeDto resourceTypeDto);

    ResourceTypeDto toResourceTypeDto(ResourceType resourceType);
}