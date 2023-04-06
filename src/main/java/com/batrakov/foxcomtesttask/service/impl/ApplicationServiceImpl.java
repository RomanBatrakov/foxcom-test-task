package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ApplicationRepository;
import com.batrakov.foxcomtesttask.exeption.ValidationException;
import com.batrakov.foxcomtesttask.mapper.ApplicationMapper;
import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.ApplicationCategory;
import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.ResourceType;
import com.batrakov.foxcomtesttask.model.Status;
import com.batrakov.foxcomtesttask.model.dto.ApplicationDto;
import com.batrakov.foxcomtesttask.service.ApplicationService;
import com.batrakov.foxcomtesttask.service.HuntingAreaService;
import com.batrakov.foxcomtesttask.service.ResourceService;
import com.batrakov.foxcomtesttask.service.ResourceTypeService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationMapper applicationMapper;
    private final ApplicationRepository applicationRepository;
    private final ResourceService resourceService;
    private final HuntingAreaService huntingAreaService;
    private final ResourceTypeService resourceTypeService;

    @Override
    public ApplicationDto createApplication(ApplicationDto applicationDto) {
        log.info("Creating application={}", applicationDto);
        Application newApplication = applicationMapper.toApplication(applicationDto);
        ApplicationCategory category = ApplicationCategory.from(applicationDto.getCategory())
                                                          .orElseThrow(
                                                                  () -> new ValidationException("Unknown category"));
        List<Resource> resources = resourceService.createResources(applicationDto.getResourcesDto());
        newApplication.setApplicationDate(LocalDate.now());
        newApplication.setCategory(category);
        newApplication.setResources(resources);
        newApplication.setStatus(Status.IN_PROGRESS);

        return applicationMapper.toApplicationDto(applicationRepository.save(newApplication));
    }

    @Override
    public Application findApplicationById(Long applicationId) {
        try {
            log.info("Finding application by Id={}", applicationId);
            return applicationRepository.findById(applicationId).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(String.format("Application with id %s is not found", applicationId));
        }
    }

    @Override
    public List<ApplicationDto> generateApplications(Long count) {
        List<HuntingArea> huntingAreaList = huntingAreaService.generateHuntingAreas(15);
        List<ResourceType> resourceTypeList = resourceTypeService.generateResourceTypes(15);


        List<Application> applications = new ArrayList<>();
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int daysToPast = faker.number().numberBetween(-1, -364);
            int daysToAdd = faker.number().numberBetween(1, 250);

            Application application = new Application();
            application.setFullName(faker.name().fullName());
            application.setTicketSeries(faker.lorem().characters(3).toUpperCase());
            application.setTicketNumber(faker.number().numberBetween(1000, 1080));
            application.setIssueDate(LocalDate.now().plusDays(daysToPast));
            application.setApplicationDate(LocalDate.now().plusDays(daysToAdd));
            application.setCategory(random.nextBoolean() ? ApplicationCategory.MASS : ApplicationCategory.LOTTERY);
            application.setStatus(Status.IN_PROGRESS);
            application.setResources(
                    resourceService.generateResources((random.nextInt(3) + 1), huntingAreaList, resourceTypeList));
            applications.add(application);
        }
        return applicationMapper.toApplicationDtoList(applicationRepository.saveAll(applications));
    }
}
