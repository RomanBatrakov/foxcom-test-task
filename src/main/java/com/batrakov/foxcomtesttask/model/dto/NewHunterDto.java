package com.batrakov.foxcomtesttask.model.dto;

import com.batrakov.foxcomtesttask.model.License;
import jakarta.validation.constraints.NotBlank;
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
public class NewHunterDto {
    private Long id;
    @NotBlank(message = " is blank or null")
    private String name;
    private License license;
}
