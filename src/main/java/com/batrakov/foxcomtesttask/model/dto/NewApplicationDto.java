package com.batrakov.foxcomtesttask.model.dto;

import com.batrakov.foxcomtesttask.model.ApplicationCategory;
import com.batrakov.foxcomtesttask.model.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewApplicationDto {
    @NotBlank(message = " is blank or null")
    private Long hunterId;
    private ApplicationCategory category;
    @NotBlank(message = " is blank or null")
    private Set<Resource> newResourcesDto;
}
