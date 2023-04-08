package com.batrakov.foxcomtesttask.model;

import java.util.Optional;

public enum Status {
    REJECTED,
    APPROVED,
    IN_PROGRESS,
    PARTIALLY_APPROVED,
    CANCELED;

    public static Optional<Status> from(String value) {
        for (Status status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
