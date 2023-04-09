package com.batrakov.foxcomtesttask.service.impl;

import com.batrakov.foxcomtesttask.dao.ApplicationRepository;
import com.batrakov.foxcomtesttask.exсeption.ValidationException;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static com.batrakov.foxcomtesttask.model.Status.APPROVED;
import static com.batrakov.foxcomtesttask.model.Status.IN_PROGRESS;
import static com.batrakov.foxcomtesttask.model.Status.PARTIALLY_APPROVED;
import static com.batrakov.foxcomtesttask.model.Status.REJECTED;
import static com.batrakov.foxcomtesttask.service.ApplicationValidator.checkApplicationDate;
import static com.batrakov.foxcomtesttask.service.ApplicationValidator.checkQuote;
import static com.batrakov.foxcomtesttask.service.ApplicationValidator.checkResourceArea;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private volatile boolean startFlag = false;
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
        List<Resource> resources = resourceService.createResources(applicationDto.getResources());
        newApplication.setApplicationDate(LocalDate.now());
        newApplication.setCategory(category);
        newApplication.setResources(resources);
        newApplication.setStatus(Status.IN_PROGRESS);
        Application application = applicationRepository.save(newApplication);
        resourceService.updateResourcesWithApplications(Collections.singletonList(application));
        return applicationMapper.toApplicationDto(application);
    }

    @Override
    public ApplicationDto updateApplication(ApplicationDto applicationDto) {
        log.info("Updating application: {}", applicationDto);
        Optional<Application> application = applicationRepository.findById(applicationDto.getId());
        if (application.isPresent()) {
            Application updatedApplication = applicationMapper.partialUpdate(applicationDto, application.get());
            updatedApplication.setStatus(IN_PROGRESS);
            updatedApplication.setResources(
                    resourceService.updateResources(applicationDto.getResources(), application.get().getResources()));
            return applicationMapper.toApplicationDto(updatedApplication);
        } else {
            throw new NoSuchElementException(
                    String.format("Application with id %s is not found", applicationDto.getId()));
        }
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
    public ApplicationDto getApplicationById(Long id) {
        Application application = findApplicationById(id);
        return applicationMapper.toApplicationDto(application);
    }

    @Override
    public List<ApplicationDto> getAllApplications() {
        log.info("Finding all applications");
        List<Application> applications = applicationRepository.findAll();
        return applicationMapper.toApplicationDtoList(applications);
    }

    @Override
    public void deleteApplication(Long id) {
        try {
            log.info("Deleting application with id={}", id);
            applicationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException(String.format("Application with id %s is not found", id));
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
        List<Application> applicationList = applicationRepository.saveAll(applications);
        resourceService.updateResourcesWithApplications(applicationList);
        return applicationMapper.toApplicationDtoList(applicationList);
    }

    @Override
    public void checkingApplications(boolean flag) {
        List<Application> applicationList = applicationRepository.findByStatus(Status.IN_PROGRESS);
        List<Application> applicationApprovedList =
                applicationRepository.findByStatusIn(List.of(APPROVED, PARTIALLY_APPROVED));
        if (flag) {
            startFlag = true;
            applicationList.sort(Comparator.comparing(Application::getApplicationDate));

            for (Application application : applicationList) {
                if (startFlag) {
                    List<Resource> resources = application.getResources();
                    for (Resource resource : resources) {
                        if (checkApplicationDate(resource, application) &&
                                checkResourceArea(resource, application, applicationList, applicationApprovedList) &&
                                checkQuote(resource, applicationList, applicationApprovedList)) {
                            resource.setStatus(APPROVED);
                        } else {
                            resource.setStatus(REJECTED);
                        }
                    }
                    application.setStatus(updateApplicationStatus(resources));
                }
            }
            applicationRepository.saveAll(applicationList);
        } else {
            startFlag = false;
            applicationRepository.saveAll(applicationList);
        }
    }

    public Status updateApplicationStatus(List<Resource> resources) {
        boolean allApproved = true;
        boolean allRejected = true;

        for (Resource resource : resources) {
            if (resource.getStatus() != Status.APPROVED) {
                allApproved = false;
            }
            if (resource.getStatus() != Status.REJECTED) {
                allRejected = false;
            }
        }

        if (allApproved) {
            return Status.APPROVED;
        } else if (allRejected) {
            return Status.REJECTED;
        } else {
            return Status.PARTIALLY_APPROVED;
        }
    }
}
