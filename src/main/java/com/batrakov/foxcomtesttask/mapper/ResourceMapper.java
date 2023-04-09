package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {
    @Mapping(target = "resourceType", ignore = true)
    @Mapping(target = "area", ignore = true)
    Resource toResource(ResourceDto resourceDto);

    @Mapping(target = "resourceTypeId", source = "resourceType.id")
    @Mapping(target = "areaId", source = "area.id")
    @Mapping(target = "applicationId", source = "application.id")
    ResourceDto toResourceDto(Resource resource);

    List<Resource> toResourceList(List<ResourceDto> resourcesDto);
}
