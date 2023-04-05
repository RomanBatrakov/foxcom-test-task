package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ApplicationRepository;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationDto createApplication(ApplicationDto applicationDto) {
        log.info("Creating application={}", applicationDto);
        return null;
    }
}
