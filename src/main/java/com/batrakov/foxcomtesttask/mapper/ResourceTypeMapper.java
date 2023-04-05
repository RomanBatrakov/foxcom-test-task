package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.dto.ResourceTypeDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceTypeMapper {
    ResourceType toResourceType(ResourceTypeDto resourceTypeDto);

    ResourceTypeDto toResourceTypeDto(ResourceType resourceType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResourceType partialUpdate(ResourceTypeDto resourceTypeDto, @MappingTarget ResourceType resourceType);
}