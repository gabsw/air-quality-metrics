package tqs.air.quality.metrics.model;

import org.junit.jupiter.api.Test;
import tqs.air.quality.metrics.utils.Equals;

class LocationDatetimeTests {
    @Test
    void checkEquals() {
        Equals.verifyEquals(LocationDatetime.class);
    }
}