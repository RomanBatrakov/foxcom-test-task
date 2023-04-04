package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.dto.NewResourcesDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {
    Resource toResource(NewResourcesDto newResourcesDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Resource partialUpdate(NewResourcesDto newResourcesDto, @MappingTarget Resource resource);
}