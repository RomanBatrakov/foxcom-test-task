package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {
    ApplicationDto createApplication(ApplicationDto applicationDto);

    Application findApplicationById(Long applicationId);

    List<ApplicationDto> generateApplications(Long count);
}
