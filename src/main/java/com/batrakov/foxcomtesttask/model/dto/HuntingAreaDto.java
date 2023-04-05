package com.batrakov.foxcomtesttask.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class HuntingAreaDto {
    private Long id;
    @Size(min = 2, message = " is to short")
    @NotBlank(message = " is blank or null")
    private String name;
}
