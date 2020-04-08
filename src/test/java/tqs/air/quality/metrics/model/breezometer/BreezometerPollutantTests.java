package tqs.air.quality.metrics.model.breezometer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BreezometerPollutantTests {

    private BreezometerPollutant pollutant;

    @BeforeEach
    void setUp() {
        pollutant = new BreezometerPollutant();
    }

    @Test
    void checkSetFullName() {
        pollutant.setFullName("Carbon monoxide");
        assertEquals("Carbon monoxide", pollutant.getFullName());
    }

    @Test
    void checkSetInitials() {
        pollutant.setInitials("CO");
        assertEquals("CO", pollutant.getInitials());
    }

    @Test
    void checkSetConcentration() {
        BreezometerConcentration concentration = new BreezometerConcentration(150.22, "ppm");
        pollutant.setConcentration(concentration);
        assertAll("set concentration",
                () -> assertEquals(150.22, pollutant.getConcentration().getValue()),
                () -> assertEquals("ppm", pollutant.getConcentration().getUnits()));
    }
}
