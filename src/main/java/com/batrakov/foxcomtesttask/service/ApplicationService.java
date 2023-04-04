package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.dto.NewApplicationDto;

public interface ApplicationService {
    Application createApplication(NewApplicationDto newApplicationDto);
}
