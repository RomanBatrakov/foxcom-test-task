package com.batrakov.foxcomtesttask.model.dto;

import com.batrakov.foxcomtesttask.model.ApplicationCategory;
import com.batrakov.foxcomtesttask.model.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private Long id;
    @NotBlank(message = " is blank or null")
    private String fullName;
    @NotBlank(message = " is blank or null")
    private String ticketSeries;
    @Positive(message = " is not positive")
    private Integer ticketNumber;
    private LocalDate issueDate;
    @NotBlank(message = " is blank or null")
    private Long hunterId;
    private ApplicationCategory category;
    @NotBlank(message = " is blank or null")
    private Set<Resource> newResourcesDto;
}
