package tqs.air.quality.metrics.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.air.quality.metrics.model.LocationDatetime;

import java.time.LocalDateTime;

public class ValidatorsTests {

    private double validLatitude = 50;
    private double invalidUpperLatitude = 100;
    private double invalidLowerLatitude = -100;
    private double validLongitude = 100;
    private double invalidUpperLongitude = 200;
    private double invalidLowerLongitude = -200;
    private LocalDateTime validLocalDateTime;
    private LocalDateTime invalidUpperLocalDateTime;
    private LocalDateTime invalidLowerLocalDateTime;

    @BeforeEach
    void setLocalDateTimes() {
        LocalDateTime currentTime = LocalDateTime.now();
        validLocalDateTime = currentTime;
        invalidUpperLocalDateTime = currentTime.plusHours(1000);
        invalidLowerLocalDateTime = currentTime.minusHours(1000);
    }


    @Test
    void whenLatitudeExceedsUpperBoundary_IllegalArgumentExceptionShouldBeThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.checkIfUserInputIsValid(new LocationDatetime(invalidUpperLatitude,
                        validLongitude, validLocalDateTime)));

        String expectedMessage = "Latitude cannot be greater than 90 degrees.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void whenLatitudeExceedsLowerBoundary_IllegalArgumentExceptionShouldBeThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.checkIfUserInputIsValid(new LocationDatetime(invalidLowerLatitude,
                        validLongitude, validLocalDateTime)));

        String expectedMessage = "Latitude cannot be less than -90 degrees.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenLongitudeExceedsUpperBoundary_IllegalArgumentExceptionShouldBeThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.checkIfUserInputIsValid(new LocationDatetime(validLatitude,
                        invalidUpperLongitude, validLocalDateTime)));

        String expectedMessage = "Longitude cannot be greater than 180 degrees.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenLongitudeExceedsLowerBoundary_IllegalArgumentExceptionShouldBeThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.checkIfUserInputIsValid(new LocationDatetime(validLatitude,
                        invalidLowerLongitude, validLocalDateTime)));

        String expectedMessage = "Longitude cannot be less than -180 degrees.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenLocalDateTimeExceedsUpperBoundary_IllegalArgumentExceptionShouldBeThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.checkIfUserInputIsValid(new LocationDatetime(validLatitude,
                        validLongitude, invalidUpperLocalDateTime)));

        String expectedMessage = "Date and time cannot exceed 96 hours into the future.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenLocalDateTimeExceedsLowerBoundary_IllegalArgumentExceptionShouldBeThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.checkIfUserInputIsValid(new LocationDatetime(validLatitude,
                        validLongitude, invalidLowerLocalDateTime)));

        String expectedMessage = "Date and time cannot exceed 720 hours into the past.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAllArgsAreValid_NoExceptionShouldBeThrown() {
        assertDoesNotThrow(() -> Validators.checkIfUserInputIsValid(new LocationDatetime(validLatitude,
                validLongitude, validLocalDateTime)));
    }

}
