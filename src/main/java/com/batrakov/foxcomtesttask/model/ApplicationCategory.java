package com.batrakov.foxcomtesttask.model;

import java.util.Optional;

public enum ApplicationCategory {
    MASS,
    LOTTERY;

    public static Optional<ApplicationCategory> from(String value) {
        for (ApplicationCategory category : values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
