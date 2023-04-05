package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {
    Resource toResource(ResourceDto resourceDto);

    ResourceDto toResourceDto(Resource resource);
}