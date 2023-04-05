package com.batrakov.foxcomtesttask.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceTypeDto {
    private Long id;
    @Size(min = 2)
    @NotBlank(message = " is blank or null")
    private String name;
    @Positive
    private Long quota;
    private LocalDate startDate;
    private LocalDate endDate;
}
