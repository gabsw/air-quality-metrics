package tqs.air.quality.metrics.validator;

import tqs.air.quality.metrics.model.LocationDatetime;

import java.time.LocalDateTime;

public class Validators {

    private Validators() {

    }

    public static void checkIfUserInputIsValid(LocationDatetime locationDatetime) {
        checkIfLatitudeIsValid(locationDatetime.getLatitude());
        checkIfLongitudeIsValid(locationDatetime.getLongitude());
        checkIfLocalDateTimeIsValid(locationDatetime.getLocalDateTime());
    }

    private static void checkIfLatitudeIsValid(double latitude) {

        if (latitude > 90) {
            throw new IllegalArgumentException("Latitude cannot be greater than 90 degrees.");
        }

        if (latitude < -90) {
            throw new IllegalArgumentException("Latitude cannot be less than -90 degrees.");
        }
    }

    private static void checkIfLongitudeIsValid(double longitude) {

        if (longitude > 180) {
            throw new IllegalArgumentException("Longitude cannot be greater than 180 degrees.");
        }

        if (longitude < -180) {
            throw new IllegalArgumentException("Longitude cannot be less than -180 degrees.");
        }
    }

    private static void checkIfLocalDateTimeIsValid(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return;
        }

        LocalDateTime currentTime = LocalDateTime.now();

        // Set by Breezometer's API limitations
        LocalDateTime maxTime = currentTime.plusHours(96);
        LocalDateTime minTime = currentTime.minusHours(720);

        if (currentTime.isAfter(maxTime)) {
            throw new IllegalArgumentException("Date and time cannot exceed 96 hours into the future.");
        }

        if (currentTime.isBefore(minTime)) {
            throw new IllegalArgumentException("Date and time cannot exceed 720 hours into the past.");
        }
    }
}
