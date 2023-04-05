package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationMapper {
    Application toApplication(ApplicationDto applicationDto);

    ApplicationDto toApplicationDto(Application application);

}
