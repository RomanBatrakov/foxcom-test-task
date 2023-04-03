package com.batrakov.foxcomtesttask.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ApplicationCategory {
    MASS("Массовые"),
    LOTTERY("Жеребьевочные");
    private final String name;

    public static Optional<ApplicationCategory> parseCardCategory(String button) {
        if (button.isBlank()) {
            return Optional.empty();
        }
        String formatName = button.trim().toLowerCase();
        return Stream.of(values()).filter(c -> c.name.equalsIgnoreCase(formatName)).findFirst();
    }
}
