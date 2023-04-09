package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "resources", ignore = true)
    Application toApplication(ApplicationDto applicationDto);

    ApplicationDto toApplicationDto(Application application);

    List<ApplicationDto> toApplicationDtoList(List<Application> applications);

    List<ResourceDto> toResourceDtoList(List<Resource> resources);

    @Mapping(target = "resourceTypeId", source = "resourceType.id")
    @Mapping(target = "areaId", source = "area.id")
    @Mapping(target = "applicationId", source = "application.id")
    ResourceDto toResourceDto(Resource resource);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "resources", ignore = true)
    Application partialUpdate(ApplicationDto applicationDto, @MappingTarget Application application);
}
