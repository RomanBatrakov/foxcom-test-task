package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.Hunter;
import com.batrakov.foxcomtesttask.model.dto.NewApplicationDto;
import com.batrakov.foxcomtesttask.service.HunterService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationMapper {
    HunterService hunterService;

    @Mapping(target = "hunter", source = "hunterId", qualifiedByName = "toHunter")
    @Mapping(target = "resources", source = "newResourcesDto", qualifiedByName = "toResources")
    @Mapping(target = "status", constant = "PENDING")
    Application toApplication(NewApplicationDto newApplicationDto);

    @Named("toHunter")
    default Hunter toHunter(Long hunterId) {
        return hunterService.findHunterById(hunterId);
    }
}
