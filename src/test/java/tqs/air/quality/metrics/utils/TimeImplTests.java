package tqs.air.quality.metrics.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeImplTests {

    private TimeImpl time = new TimeImpl();

    @Test
    void checkNow() {
        LocalDateTime currentTime = LocalDateTime.now();
        assertTrue(currentTime.isBefore(time.now()) || currentTime.isEqual(time.now()));
    }
}
