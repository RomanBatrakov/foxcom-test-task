package com.batrakov.foxcomtesttask.model.dto;

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
public class ResourceDto {
    private Long id;
    @Positive(message = " is not positive")
    private Long resourceTypeId;
    @Positive(message = " is not positive")
    private Long areaId;
    @Positive(message = " is not positive")
    private Long amount;
    @Positive(message = " is not positive")
    private Long applicationId;
}
