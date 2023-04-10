package com.batrakov.foxcomtesttask.model.dto;

import com.batrakov.foxcomtesttask.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @PastOrPresent(message = " is not past")
    private LocalDate issueDate;
    private LocalDate applicationDate;
    private String category;
    private Status status;
    @NotEmpty(message = " is empty")
    @NotNull(message = " is null")
    private List<ResourceDto> resources;
}
