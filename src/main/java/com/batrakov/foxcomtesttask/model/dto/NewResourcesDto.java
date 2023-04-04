package com.batrakov.foxcomtesttask.model.dto;

import com.batrakov.foxcomtesttask.model.HuntingArea;
import com.batrakov.foxcomtesttask.model.ResourceType;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewResourcesDto {
    private ResourceType resourceType;
    private HuntingArea area;
    @Positive(message = " is not positive")
    private Long amount;
}
