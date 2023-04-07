package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.model.dto.ResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
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

    List<Resource> toResourceList(List<ResourceDto> resourcesDto);

    List<ResourceDto> toResourceDtoList(List<Resource> resources);

    @Mapping(target = "resourceType", ignore = true)
    @Mapping(target = "area", ignore = true)
    Resource toResource(ResourceDto resourceDto);

    @Mapping(target = "resourceTypeId", source = "resourceType.id")
    @Mapping(target = "areaId", source = "area.id")
    ResourceDto toResourceDto(Resource resource);
}
