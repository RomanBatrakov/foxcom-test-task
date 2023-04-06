package com.batrakov.foxcomtesttask.mapper;

import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.dto.HuntingAreaDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HuntingAreaMapper {
    HuntingArea toHuntingArea(HuntingAreaDto huntingAreaDto);

    HuntingAreaDto toHuntingAreaDto(HuntingArea huntingArea);
}