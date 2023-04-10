package com.batrakov.foxcomtesttask.service;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.Resource;
import com.batrakov.foxcomtesttask.model.ResourceType;

import java.util.List;
import java.util.stream.Collectors;

import static com.batrakov.foxcomtesttask.model.Status.APPROVED;

public class ApplicationValidator {
    public static boolean checkQuote(Resource res, List<Application> applicationList,
                                     List<Application> applicationApprovedList) {
        Long amount1 = getAmountQuoteFromList(res, applicationList);
        Long amount2 = getAmountQuoteFromList(res, applicationApprovedList);
        Long quota = res.getResourceType().getQuota();
        return (quota - amount1 - amount2 - res.getAmount()) >= 0;
    }

    private static Long getAmountQuoteFromList(Resource res, List<Application> applicationList) {
        return applicationList.stream()
                              .flatMap(application -> application.getResources().stream())
                              .filter(resource -> resource.getResourceType().equals(res.getResourceType()))
                              .filter(resource -> resource.getStatus().equals(APPROVED))
                              .mapToLong(Resource::getAmount)
                              .sum();
    }

    public static boolean checkResourceArea(Resource resource, Application application,
                                            List<Application> applicationList,
                                            List<Application> applicationApprovedList) {
        HuntingArea area = resource.getArea();
        ResourceType type = resource.getResourceType();
        String ticketSeries = application.getTicketSeries();
        Integer ticketNumber = application.getTicketNumber();

        List<Application> applicationList1 =
                filterApplications(applicationList, area, type, ticketSeries, ticketNumber);
        List<Application> applicationList2 =
                filterApplications(applicationApprovedList, area, type, ticketSeries, ticketNumber);

        return applicationList1.isEmpty() && applicationList2.isEmpty();
    }

    private static List<Application> filterApplications(List<Application> applicationList, HuntingArea area,
                                                        ResourceType resourceType, String ticketSeries,
                                                        Integer ticketNumber) {
        return applicationList.stream()
                              .filter(application -> application.getResources()
                                                                .stream()
                                                                .anyMatch(
                                                                        resource -> !resource.getArea().equals(area) &&
                                                                                resource.getResourceType()
                                                                                        .equals(resourceType) &&
                                                                                resource.getStatus().equals(APPROVED) &&
                                                                                application.getTicketSeries()
                                                                                           .equals(ticketSeries) &&
                                                                                application.getTicketNumber()
                                                                                           .equals(ticketNumber)))
                              .collect(Collectors.toList());
    }

    public static boolean checkApplicationDate(Resource resource, Application application) {
        return (application.getApplicationDate().isAfter(resource.getResourceType().getStartDate()) &&
                application.getApplicationDate().isBefore(resource.getResourceType().getEndDate())) ||
                application.getApplicationDate().isEqual(resource.getResourceType().getStartDate()) ||
                application.getApplicationDate().isEqual(resource.getResourceType().getEndDate());
    }
}
